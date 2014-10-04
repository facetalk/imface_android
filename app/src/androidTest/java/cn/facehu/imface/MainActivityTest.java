package cn.facehu.imface;

import android.app.Activity;
import android.view.MenuItem;

import com.facehu.imface.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.tester.android.view.TestMenuItem;

/**
 * Created by hxb on 2014/10/3.
 */
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class MainActivityTest {
    Activity activity;

    @Before
    public void before() {
//        activity = Robolectric
//                .buildActivity(MainActivity_.class)
//                .create().get();
    }


    private final MenuItem actionSettingsMenuItem = new TestMenuItem() {
        @Override
        public int getItemId() {
            return R.id.action_settings;
        }
    };

    @Test
    public void testActionSettings() throws Exception {
//        TextView text = (TextView) activity.findViewById(R.id.text);
//
//        activity.onOptionsItemSelected(actionSettingsMenuItem);
//        assertThat(text.getText().toString(), equalTo("action_settings"));
    }

    @Test
    public void testButtonClick() throws Exception {
//        Button button = (Button) activity.findViewById(R.id.button);
//        TextView text = (TextView) activity.findViewById(R.id.text);
//
//        button.performClick();
//        assertThat(text.getText().toString(), equalTo("button"));
    }
}