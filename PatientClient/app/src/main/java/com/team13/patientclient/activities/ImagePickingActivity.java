package com.team13.patientclient.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;

public class ImagePickingActivity {
    public static final int PICK_IMAGE_REQUEST = 321;
    //private StorageReference mStorageRef;

    public ImagePickingActivity(){
        //mStorageRef = FirebaseStorage.getInstance().getReference("upload");
    }

    public static Intent fileChooserIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return intent;
    }

    public String getFileExtension(Uri uri, ContentResolver cr){
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

//    public UploadTask uploadFile(Uri uri, ContentResolver cr){
//        if (uri != null){
//            StorageReference fileRef = mStorageRef.child(System.currentTimeMillis() + '.' + getFileExtension(uri,cr));
//
//            return fileRef.putFile(uri);
//        }
//        return null;
//    }

}
