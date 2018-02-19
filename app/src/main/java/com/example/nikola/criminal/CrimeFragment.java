package com.example.nikola.criminal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.nikola.criminal.database.Crime;
import com.example.nikola.criminal.database.CrimeLabHelper;
import com.example.nikola.criminal.util.PictureUtil;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "pick_a_date";
    private static final String DIALOG_TIME = "pick_time";
    private static final String TAG = "Crime Fragment";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CONTACT = 101;
    private static final int REQUEST_CONTACT_CALL = 102;
    private static final int REQUEST_PHOTO = 2;
    private Crime mCrime = new Crime();
    private Button mDateButton;
    private Button mTimeButton;
    private Button mReportButton;
    private Button mSuspectButton;
    private Button mCallSuspect;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;
    private CompositeDisposable mCompositeDisposable;


    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhotoFile = CrimeLabHelper.getInstance(getActivity()).getPhotoFile(mCrime);
        setHasOptionsMenu(true);
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        mDateButton = v.findViewById(R.id.crime_date);
        final CheckBox solvedCheckBox = v.findViewById(R.id.crime_solved);
        final EditText titleField = v.findViewById(R.id.crime_title);
        final UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);

        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(CrimeLabHelper.getInstance(getActivity()).getCrimeById(crimeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()
                ).subscribeWith(new DisposableSingleObserver<Crime>() {
                    @Override
                    public void onSuccess(Crime crime) {
                        mCrime = crime;
                        titleField.setText(mCrime.getTitle());
                        updateDate();
                        int hour = mCrime.getHour();
                        int minutes = mCrime.getMinute();
                        mTimeButton.setText(String.format("%d:%02d", hour, minutes));
                        solvedCheckBox.setChecked(mCrime.isSolved());

                        if (mCrime.getSuspect() != null) {
                            mSuspectButton.setText(mCrime.getSuspect());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }));


        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    titleField.setError(getString(R.string.edit_text_empty_warning));
                } else {
                    mCompositeDisposable.add(CrimeLabHelper.getInstance(getActivity()).getCrimeById(crimeId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableSingleObserver<Crime>() {
                                @Override
                                public void onSuccess(Crime crime) {
                                    mCrime.setTitle(String.valueOf((charSequence)));
                                    populateWithCrimes(mCrime);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            }));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    titleField.setError(getString(R.string.edit_text_empty_warning));
                }
            }
        });

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (screenSize() < 450) {
                    Intent intent = DatePickerActivity.onNewIntent(getActivity(), mCrime.getDate());
                    startActivityForResult(intent, REQUEST_DATE);
                } else {
                    FragmentManager manager = getFragmentManager();
                    DatePickerFragment dialog = DatePickerFragment
                            .onNewInstance(mCrime.getDate());
                    dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                    dialog.show(manager, DIALOG_DATE);
                }
            }

        });

        mTimeButton = v.findViewById(R.id.crime_time);


        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment timeFragment = new TimePickerFragment();
                timeFragment.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                timeFragment.show(manager, DIALOG_TIME);
            }
        });


        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean setChecked) {
                mCompositeDisposable.add(CrimeLabHelper.getInstance(getActivity()).getCrimeById(crimeId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Crime>() {
                            @Override
                            public void onSuccess(Crime crime) {
                                mCrime.setSolved(setChecked);
                                populateWithCrimes(mCrime);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        }));

            }
        });

        mReportButton = v.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(getCrimeReport())
                        .setSubject(getString(R.string.crime_report_subject))
                        .setChooserTitle(R.string.send_report)
                        .startChooser();
            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mSuspectButton = v.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        mCallSuspect = v.findViewById(R.id.call_suspect);
        mCallSuspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + mCrime.getSuspectNumber()));
                startActivity(intent);

            }
        });

        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mSuspectButton.setEnabled(false);
        }


        mPhotoButton = v.findViewById(R.id.crime_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                providing URI of the picture storage location to enable high resolution pictures

                Uri uri = FileProvider.getUriForFile(getActivity(), "com.example.nikola.criminal.fileprovider", mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity().getPackageManager().queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
//                    grand Camera app permission to every activity that the CameraImage intent will resolve to.
                    getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }


        });

        mPhotoView = v.findViewById(R.id.crime_photo);
        updatePhotoView();

        return v;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_item:
                deleteCrime(mCrime);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteCrime(final Crime crime) {
        mCompositeDisposable.add(CrimeLabHelper.getInstance(getActivity()).deleteCrime(crime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Crime successfully deleted ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
            populateWithCrimes(mCrime);
        } else if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};

            Cursor cursor = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);

            try {
                if (cursor.getCount() == 0) {
                    return;
                }
                cursor.moveToFirst();
                String suspect = cursor.getString(0);

                mCrime.setSuspect(suspect);
                mSuspectButton.setText(suspect);
            } finally {
                cursor.close();
            }

        } else if (requestCode == REQUEST_CONTACT_CALL) {
            Uri contactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] queryFields = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            String whereClause = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ";

            Cursor cursor = getActivity().getContentResolver().query(contactsUri, queryFields, whereClause, null, null);

            try {
                if (cursor.getCount() != 0) {
                    return;
                }
                cursor.moveToFirst();
                String suspectPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                mCrime.setSuspectNumber(suspectPhone);
                mCallSuspect.setText(suspectPhone);
                Log.d("CrimeFragment", suspectPhone);

            } finally {
                cursor.close();
            }
        } else if (requestCode == REQUEST_TIME) {
            int hour = data.getIntExtra(TimePickerFragment.H, 0);
            int minute = data.getIntExtra(TimePickerFragment.M, 0);
            mCrime.setHour(hour);
            mCrime.setMinute(minute);
            mTimeButton.setText(String.valueOf(mCrime.getHour() + ":" + mCrime.getMinute()));
            populateWithCrimes(mCrime);

        } else if (requestCode == REQUEST_PHOTO) {

            Uri uri = FileProvider.getUriForFile(getActivity(), "com.example.nikola.criminal.fileprovider", mPhotoFile);
            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }

    private void updateDate() {
        mDateButton.setText(DateFormat.getDateInstance(DateFormat.FULL).format(mCrime.getDate()));
    }

    private String getCrimeReport() {
        String solvedString = null;

        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateString = DateFormat.getDateInstance(DateFormat.FULL).format(mCrime.getDate());

        String suspect = mCrime.getSuspect();

        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        return getString(R.string.crime_report,
                mCrime.getTitle(), dateString, solvedString, suspect);
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtil.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    private void populateWithCrimes(Crime crime) {
        mCompositeDisposable.add(CrimeLabHelper.getInstance(getActivity()).insertCrime(crime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Crime successfully inserted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    private float screenSize() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        float scaleFactor = metrics.density;

        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;

        return Math.min(widthDp, heightDp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.clear();
    }
}

