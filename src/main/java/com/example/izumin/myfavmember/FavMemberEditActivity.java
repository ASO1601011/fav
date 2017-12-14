package com.example.izumin.myfavmember;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class FavMemberEditActivity extends AppCompatActivity {
    private Realm mRealm;
    EditText mNumEdit;
    EditText mNameEdit;
    EditText mPitchEdit;
    EditText mMusicEdit;
    Button mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_member_edit);
        mRealm = Realm.getDefaultInstance();
        mNumEdit = (EditText)findViewById(R.id.numEdit);
        mNameEdit = (EditText)findViewById(R.id.nameEdit);
        mPitchEdit = (EditText)findViewById(R.id.pitchEdit);
        mMusicEdit = (EditText)findViewById(R.id.musicEdit);
        mDelete = (Button) findViewById(R.id.delete);

        long favMemberId = getIntent().getLongExtra("favMember_id",-1);
        if(favMemberId != -1){
            RealmResults<FavMember> results = mRealm.where(FavMember.class)
                    .equalTo("id",favMemberId).findAll();
            FavMember favMember = results.first();
            mNumEdit.setText(String.valueOf(favMember.getNum()));
            mNameEdit.setText(favMember.getName());
            mPitchEdit.setText(favMember.getPitch());
            mMusicEdit.setText(favMember.getMusic());
            mDelete.setVisibility(View.VISIBLE);
        }else{
            mDelete.setVisibility(View.INVISIBLE);
        }
    }

    public void onSaveTapped(View view){

        long favMemberId = getIntent().getLongExtra("favMember_id",-1);
        if(favMemberId != -1){
            final RealmResults<FavMember> results = mRealm.where(FavMember.class)
                    .equalTo("id",favMemberId).findAll();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm){
                    FavMember favMember = results.first();
                    favMember.setNum(Integer.parseInt(mNumEdit.getText().toString()));
                    favMember.setName(mNameEdit.getText().toString());
                    favMember.setPitch(mPitchEdit.getText().toString());
                    favMember.setMusic(mMusicEdit.getText().toString());
                }
            });
            Snackbar.make(findViewById(android.R.id.content),
                    "アップデートしました", Snackbar.LENGTH_LONG)
                    .setAction("戻る",new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            finish();
                        }
                    })
                    .setActionTextColor(Color.YELLOW)
                    .show();
        }else{
            mRealm.executeTransaction(new Realm.Transaction(){
                @Override
                public void execute(Realm realm){
                    Number maxId = realm.where(FavMember.class).max("id");
                    long nextId = 0;
                    if(maxId != null) nextId = maxId.longValue() + 1;
                    FavMember favMember = realm.createObject(FavMember.class,new Long(nextId));
                    favMember.setNum(Integer.parseInt(mNumEdit.getText().toString()));
                    favMember.setName(mNameEdit.getText().toString());
                    favMember.setPitch(mPitchEdit.getText().toString());
                    favMember.setMusic(mMusicEdit.getText().toString());
                }
            });
            Toast.makeText(this,"追加しました", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onDeleteTapped(View view){
        final long favMemberId = getIntent().getLongExtra("favMember_id", -1);
        if(favMemberId != -1){
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm){
                    FavMember favMember = realm.where(FavMember.class)
                            .equalTo("id",favMemberId).findFirst();
                    favMember.deleteFromRealm();
                }
            });
            Toast.makeText(this,"削除しました", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
