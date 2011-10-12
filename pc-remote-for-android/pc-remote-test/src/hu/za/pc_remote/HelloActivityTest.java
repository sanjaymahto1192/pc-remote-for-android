package hu.za.pc_remote;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class hu.za.pc_remote.HelloActivityTest \
 * hu.za.pc_remote.tests/android.test.InstrumentationTestRunner
 */
public class HelloActivityTest extends ActivityInstrumentationTestCase2<HelloActivity> {

    private HelloActivity mActivity;
    private TextView mView;
    private String resourceString;

    public HelloActivityTest() {
        super("hu.za.pc_remote", HelloActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
        mView = (TextView) mActivity.findViewById(R.id.textView1);
        resourceString = mActivity.getString(R.string.hello);
    }

    public void testPreconditions(){
        assertNotNull(mView);
    }

    public void testText() {
      assertEquals(resourceString,(String)mView.getText());
    }
}
