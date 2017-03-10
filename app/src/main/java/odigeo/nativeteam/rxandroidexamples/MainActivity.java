package odigeo.nativeteam.rxandroidexamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    Button buttonJust;
    Button buttonDefer;
    Button buttonFrom;
    Button buttonMap;

    Button buttonJust2;
    Button buttonDefer2;
    Button buttonMap2;

    private Observable<List<Integer>> integerListObservable = Observable.fromCallable(() -> generateIntegerList());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.exampleTextView);

        buttonJust = (Button) findViewById(R.id.buttonJust);
        buttonDefer = (Button) findViewById(R.id.buttonDefer);
        buttonFrom = (Button) findViewById(R.id.buttonFrom);
        buttonMap = (Button) findViewById(R.id.buttonMap);

        buttonJust2 = (Button) findViewById(R.id.buttonJust2);
        buttonDefer2 = (Button) findViewById(R.id.buttonDefer2);
        buttonMap2 = (Button) findViewById(R.id.buttonMap2);

        setButtonListeners();
    }

    private void setButtonListeners() {
        buttonJust.setOnClickListener(view -> onButtonJustClicked());
        buttonDefer.setOnClickListener(view -> onButtonDeferClicked());
        buttonFrom.setOnClickListener(view -> onButtonFromClicked());
        buttonMap.setOnClickListener(view -> onButtonMapClicked());

        buttonJust2.setOnClickListener(view -> onButtonJust2Clicked());
        buttonDefer2.setOnClickListener(view -> onButtonDefer2Clicked());
        buttonMap2.setOnClickListener(view -> onButtonMap2Clicked());
    }




    /*
    JUST EXAMPLES
     */

    private void onButtonJustClicked() {
        Observable.just(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(() -> textView.setText(""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> paintInteger(integer));
    }

    private void onButtonJust2Clicked() {
        Single.just(generateIntegerList())
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(() -> textView.setText(""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integerList -> paintListOfIntegers(integerList));
    }






    /*
    DEFER EXAMPLES
     */
    private void onButtonDeferClicked() {
        Observable.defer(() -> Observable.just(generateIntegerList()))
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(() -> textView.setText(""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integerList -> paintListOfIntegers(integerList));
    }

    private void onButtonDefer2Clicked() {
        integerListObservable
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(() -> textView.setText(""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integerList -> paintListOfIntegers(integerList));
    }







    /*
    FROM EXAMPLE
     */
    private void onButtonFromClicked() {
        Observable.from(generateIntegerList())
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(() -> textView.setText(""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> paintInteger(integer));
    }











    /*
    MAP EXAMPLES
     */
    private void onButtonMapClicked() {
        Observable.just(generateIntegerList())
                .subscribeOn(Schedulers.newThread())
                .map(integerList -> integerList.subList(0,3))
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(() -> textView.setText(""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integerList -> paintListOfIntegers(integerList));
    }

    private void onButtonMap2Clicked() {
        Observable.from(generateIntegerList())
                .subscribeOn(Schedulers.newThread())
                .map(integer -> integer*2)
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(() -> textView.setText(""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> paintInteger(integer));
    }

    private void onButtonMap3Clicked() {
        Observable.from(generateIntegerList())
                .subscribeOn(Schedulers.newThread())
                .map(integer -> textExamples[integer])
                .doOnSubscribe(() -> textView.setText(""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> paintString(text));
    }








    /*
    UTILS
     */
    private List<Integer> generateIntegerList() {
        List<Integer> returnList = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            returnList.add(i);
        }

        return returnList;
    }

    private void paintInteger(Integer integer) {
        String text = textView.getText().toString();

        text += integer + "\n";

        textView.setText(text);
    }

    private void paintString(String word) {
        String text = textView.getText().toString();

        text += word + "\n";

        textView.setText(text);
    }

    private void paintListOfIntegers(List<Integer> integerList) {
        String text = textView.getText().toString();
        boolean first = true;
        for (int i = 0; i < integerList.size(); ++i) {
            if (first) {
                text += integerList.get(i);
                first = false;
            } else {
                text += ", " + integerList.get(i);
            }
        }
        textView.setText(text + "\n");
    }

    private String[] textExamples = new String[] {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
}
