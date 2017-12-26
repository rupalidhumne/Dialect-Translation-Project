package com.example.rdhumne.mobilefinal;


import android.annotation.TargetApi;
import android.os.Build;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import java.util.List;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Locale;
import android.util.Log;
import android.text.TextUtils;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Context;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;




public class MainActivity extends AppCompatActivity implements RecognitionListener
{
    public static final int LISTENING_TIMEOUT = 4000;
    private TextView resultTEXT;
    private TextView errorText;
    private ImageButton imageButton;
    Context context;
    String tag = "";
    TextToSpeech ttobj;
    private List<String> mSentences;
    private SpeechRecognizer mSr= SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
    private String listenedWords=""; //hello my name is, turn into a phrase, for all words after that phrase speak those words and then add to listened words
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=MainActivity.this;
        resultTEXT= (TextView)findViewById(R.id.TVResult);
        //resultTEXT= (TextView)findViewById(R.id.EResult);
        imageButton = (ImageButton)findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                resultTEXT.setText("reached");
                Log.i("Foo", "reached on click");
                //resultTEXT.setText("Reached on click");
                if (v.getId() == R.id.imageButton)
                {

                    doSomething();

                }
            }
        });

    }

    private void doSomething()
    {

        Intent intentRecognizer = createRecognizerIntent();
        mSr.setRecognitionListener(MainActivity.this);
        mSr.startListening(intentRecognizer);
        listenedWords="";

    }



    private Intent createRecognizerIntent()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplicationContext().getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplication().getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,  "en");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 5L);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,
                5L);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");

        return intent;
    }

    private void setErrorMessage(int res)
    {
        resultTEXT.setText(getString(res));
        errorText.setVisibility(View.VISIBLE);
    }

    private void setPartialResult(String[] results)
    {
        String result=TextUtils.join(" · ", results);
        resultTEXT.setText(result);

    }
    /*
    private void outputPartialResult(String[] results)
    {
        String result=TextUtils.join(" · ", results);
        String[] splits=result.split(listenedWords);
        Log.i(tag, splits.toString());
        output(splits[1]); //add texttospeech element here
        listenedWords=result;
                //"I like to eat", "bananas" say bananas
   }
    */
   private void output(final String s)
   {
           ttobj= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
           @Override
           public void onInit(int status)
           {
               if(status !=TextToSpeech.ERROR)
               {
                   ttobj.setLanguage(Locale.US);
                   ttsGreater21(s, ttobj);

               }
           }
       });






   }
    /*
    public static String getAccessToken(String charset, String clientId, String clientSecret, String scope,
                                        String grantType) throws MalformedURLException, IOException
    {
        String url = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";
        String paramsTemplate = "client_id=%s&client_secret=%s&scope=%s&grant_type=%s";
        String params = String.format(paramsTemplate, URLEncoder.encode(clientId, charset),
                URLEncoder.encode(clientSecret, charset), scope, grantType);
        System.out.println(url);
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=" + charset);
        conn.setRequestProperty("Accept-Charset", charset);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        //IOUtils.write(params, conn.getOutputStream(), "UTF-8");;
        //String resp = IOUtils.toString(conn.getInputStream(), "UTF-8");
        //System.out.println(resp);
        //String accessToken = JSON.parseObject(resp).getString("access_token");
       // return accessToken;
    }
    public String translate(String s) throws MalformedURLException, IOException
    {
        String charset = StandardCharsets.UTF_8.name();
        String clientId = "rupali-translator-test";
        String clientSecret = "xxxxxxxxxxxxxxxxxx";
        String scope = "http://api.microsofttranslator.com";
        String grantType = "client_credentials";
        String accessToken = getAccessToken(charset, clientId, clientSecret, scope, grantType);
        System.out.println(accessToken);
        String text = "happy";
        String from = "en";
        String to = "es";
        String resp = translate(charset, accessToken, text, from, to);
        return resp;
    }
    */
    public String translateHelper()
    {
        return "";
    }
   @TargetApi(Build.VERSION_CODES.LOLLIPOP)
   private void ttsGreater21(String text, TextToSpeech tts)
   {
       String utteranceId=this.hashCode() + "";
       tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
   }

    @Override
    public void onReadyForSpeech(Bundle params)
    {

        resultTEXT.setText("Ready");
        Log.i(tag, "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech()
    {
        Log.i(tag, "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB)
    {
//       Log.i("RMS", ""+rmsdB);
//        Log.i(tag, "onRmsChanged");
    }

    @Override
    public void onBufferReceived(byte[] buffer)
    {
        Log.i(tag, "onBufferReceived");
    }

    @Override
    public void onEndOfSpeech()
    {
        Log.i(tag, "onEndOfSpeech");
        //resultTEXT.setText("CHEESE its taste good");
        output(resultTEXT.getText().toString());
    }

    @Override
    public void onError(int error) {


        String mError = "";
        switch (error) {
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                mError = " network timeout";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                mError = " network";
                return;
            case SpeechRecognizer.ERROR_AUDIO:
                mError = " audio";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                mError = " server";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                mError = " client";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                mError = " speech time out";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                mError = " no match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                mError = " recogniser busy";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                mError = " insufficient permissions";
                break;

        }
        resultTEXT.setText(mError);
        Log.e(tag, "onError " + mError);

    }

    @Override
    public void onResults(Bundle results)
    {
        Log.i(tag,"onResults");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        //String translatedString=translate(matches.get(0));

        resultTEXT.setText(""+matches.get(0));
        Log.e(tag,"onResults" + matches.toString());

    }

    @Override
    public void onPartialResults(Bundle partialResults)
    {
        ArrayList<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String[] arr = matches.toArray(new String[matches.size()]);
        //String translatedString=translate();
        setPartialResult(arr);

        //outputPartialResult(arr);
    }

    @Override
    public void onEvent(int eventType, Bundle params)
    {
        Log.i(tag, "onEvent");
    }
}