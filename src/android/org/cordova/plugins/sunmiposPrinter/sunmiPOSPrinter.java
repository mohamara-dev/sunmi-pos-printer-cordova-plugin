
package org.cordova.plugin.sunmi.sunmiposprinter;

import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;
import com.sunmi.printerhelper.activity.MainActivity;

// Needed only for fake API calls
import java.util.HashMap;
import java.util.Map;

public class sunmiPOSPrinter extends CordovaPlugin {
  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    // Plugin specific one off initialization code here, this one doesn't
    // have any
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    // Which method was called? With many methods in a
    // plugin we could do this another way e.g. reflection
    if ("print".equals(action)) {
      print(args.getString(0), args.getString(1));
      return true;
    }
    if ("feed".equals(action)) {
      paperFeedForward();
      return true;
    }
    if ("printText".equals(action)) {
      printText(args.getString(0));
      return true;
    }
    if ("printBitmap".equals(action)) {
      printBitmap(args.getString(0));

      return true;
    }
    if ("printQr".equals(action)) {
      printQr(args.getString(0));
      return true;
    }
    if ("doSomethingNoArgs".equals(action)) {
      doSomethingNoArgs(callbackContext);
      return true;
    } else if ("doSomethingOneArg".equals(action)) {
      doSomethingOneArg(args.getString(0), callbackContext);
      return true;
    } else if ("doSometingMultipleArgs".equals(action)) {
      doSomethingMultipleArgs(args.getString(0), args.getBoolean(1), args.getJSONArray(2), callbackContext);
      return true;
    }

    // No action matched
    return false;
  }

  private void doSomethingNoArgs(CallbackContext callbackContext) {
    boolean success = false;

    // Call some native API and set success to true|false
    int result = callSomeApi();
    success = !(result == -1);
    // End call some native API

    if (success) {
      callbackContext.success(result);
    } else {
      callbackContext.error("Some native API failed!");
    }
  }

  private void doSomethingOneArg(String arg, CallbackContext callbackContext) {
    // TODO
    boolean success = false;

    // Call some native API and set success to true|false
    String result = callSomeApi(arg);
    success = (result != null);
    // End call some native API

    if (success) {
      callbackContext.success(result);
    } else {
      callbackContext.error("Some native API failed!");
    }

  }

  private void doSomethingMultipleArgs(String argStr, boolean argBool, JSONArray argArray,
      CallbackContext callbackContext) {
    boolean success = false;

    // Check argArray
    if (argArray == null || argArray.length() == 0) {
      callbackContext.error("doSomethingMultipleArgs requires a populated argArray!");
    } else {
      // Call some native API and set success to true|false
      String[] stringArray = new String[argArray.length()];

      for (int n = 0; n < argArray.length(); n++) {
        try {
          stringArray[n] = argArray.getString(n);
        } catch (JSONException jsone) {
          callbackContext.error("doSomethingMultipleArgs found a non-String value in argArray!");
          return;
        }
      }

      Map<String, String> result = callSomeApi(argStr, argBool, stringArray);
      success = (result != null && !result.isEmpty());
      // End call some native API

      if (success) {
        // Pack result up into a JSON Object
        JSONObject resObj = new JSONObject();
        for (Map.Entry<String, String> entry : result.entrySet()) {
          try {
            resObj.put(entry.getKey(), entry.getValue());
          } catch (JSONException jsone) {
            callbackContext.error("JSONException marshalling return object!");
          }
        }

        // TODO
        callbackContext.success(resObj);
      } else {
        callbackContext.error("Some native API failed!");
      }
    }
  }

  // ***** The rest of this class represent dummy API methods *****
  // ***** and are not as such part of the Cordova plugin *****

  // Dummy API call returns an int
  private int callSomeApi() {
    return 42;
  }

  // Dummy API call returns a String, uses single parameter
  private String callSomeApi(String param) {
    String result = null;

    if (param != null && param.length() > 0) {
      result = new StringBuilder(param).reverse().toString();
    }

    return result;
  }

  // Dummy API call returns a Map, uses parameters
  private Map<String, String> callSomeApi(String prefix, boolean reverseStrings, String[] stringArray) {
    Map<String, String> m = new HashMap<String, String>();

    for (int n = 0; n < stringArray.length; n++) {
      String str = (reverseStrings ? new StringBuilder(stringArray[n]).reverse().toString() : stringArray[n]);
      m.put(prefix + Integer.toString(n), str);
    }

    return m;
  }

  public void print(String action, String message) {

    // Intent intentPrinter = new
    // Intent(this.cordova.getActivity().getBaseContext(), PrinterActivity.class);
    // // intentPrinter.setAction(Intents.Print.ACTION);
    // intentPrinter.putExtra("action", action);
    // intentPrinter.putExtra("message", message);
    // // avoid calling other phonegap apps
    // intentPrinter.setPackage(this.cordova.getActivity().getApplicationContext().getPackageName());

    // this.cordova.getActivity().startActivity(intentPrinter);
  }

  public void printText(String message) {

    // Intent intentPrinter = new
    // Intent(this.cordova.getActivity().getBaseContext(), PrinterActivity.class);
    // // intentPrinter.setAction(Intents.Print.ACTION);
    // intentPrinter.putExtra("action", "0");
    // intentPrinter.putExtra("message", message);
    // // avoid calling other phonegap apps
    // intentPrinter.setPackage(this.cordova.getActivity().getApplicationContext().getPackageName());

    // this.cordova.getActivity().startActivity(intentPrinter);
  }

  public void printBitmap(String message) {

    Intent intentPrinter = new Intent(this.cordova.getActivity().getBaseContext(), MainActivity.class);

    // ent(this.cordova.getActivity().getBaseContext(), PrinterActivity.class);
    // intentPrinter.setAction(Intents.Print.ACTION);
    intentPrinter.putExtra("message", message);
    // avoid calling other phonegap apps
    intentPrinter.setPackage(this.cordova.getActivity().getApplicationContext().getPackageName());
    // Log.i("posprinter logs", "getPackageName : " + intentPrinter);
    // this.cordova
    this.cordova.getActivity().startActivity(intentPrinter);

  }

  public void printQr(String message) {

    // Intent intentPrinter = new
    // Intent(this.cordova.getActivity().getBaseContext(), PrinterActivity.class);
    // // intentPrinter.setAction(Intents.Print.ACTION);
    // intentPrinter.putExtra("action", "2");
    // intentPrinter.putExtra("message", message);
    // // avoid calling other phonegap apps
    // intentPrinter.setPackage(this.cordova.getActivity().getApplicationContext().getPackageName());

    // this.cordova.getActivity().startActivity(intentPrinter);
  }

  public void paperFeedForward() {

    // Intent intentPrinter = new
    // Intent(this.cordova.getActivity().getBaseContext(), PrinterActivity.class);
    // // intentPrinter.setAction(Intents.Print.ACTION);
    // intentPrinter.putExtra("action", "3");
    // intentPrinter.putExtra("message", "");
    // // avoid calling other phonegap apps
    // intentPrinter.setPackage(this.cordova.getActivity().getApplicationContext().getPackageName());

    // this.cordova.getActivity().startActivity(intentPrinter);
  }
}
