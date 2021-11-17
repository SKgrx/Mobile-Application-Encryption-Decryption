package com.example.a13352289app2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    EditText inputTxt, inputPw;
    TextView outputTxt;
    Button encBt, decBt;
    String outputString, encryptedValue, decryptedValue; String AES="AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputTxt = (EditText) findViewById(R.id.inputText);
        inputPw = (EditText) findViewById(R.id.inputPass);
        outputTxt = (TextView) findViewById(R.id.outputText);
        encBt = (Button) findViewById(R.id.encBtn);
        decBt = (Button) findViewById(R.id.decBtn);

        encBt.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) { try {
            outputString = encrypt(inputTxt.getText().toString(),
                    inputPw.getText().toString());
            outputTxt.setText(outputString);
        } catch (Exception e) {
            e.printStackTrace(); }
        }
        });

        decBt.setOnClickListener(new View.OnClickListener() { @Override
        public void onClick(View v) { try {
            outputString = decrypt(outputString, inputPw.getText().toString());
        } catch (Exception e) {
            Toast.makeText(MainActivity.this,"Incorrect password, try again please", Toast.LENGTH_LONG).show();
            e.printStackTrace(); }
            outputTxt.setText(outputString); }
        });
    }

    private String encrypt (String Data, String pass) throws Exception {
        SecretKeySpec key = generateKey(pass);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue; }

    private String decrypt (String outputString, String pass) throws Exception {
        SecretKeySpec key = generateKey(pass);
        Cipher c = Cipher.getInstance(AES); c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decode(outputString, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodedValue);
        decryptedValue = new String(decValue);
        return decryptedValue; }

    private SecretKeySpec generateKey (String pass) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = pass.getBytes("UTF-8"); digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec; }
}
