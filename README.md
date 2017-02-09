Mooshimeter-AndroidApp-34beta-BcastIntent

Summary:  Changes allow broadcast of all three channels in a single intent, even in background.

Details:  These are my three changes to the excellent Mooshimeter app.  I used the beta tester version 34 of the app https://github.com/mooshim/Mooshimeter-AndroidApp/tree/beta.   I claim no rights or credits to the changes I made and I hope at least some portion of these changes get incorporated back into a formal Mooshimeter app.

1.    common.BroadcastIntentData    has been changed to broadcast all three channels in a single intent.  This will give an idea on how:

intent.putExtra("units1", valCH1.units);  // key, value pair

intent.putExtra("value1", valCH1.value);

intent.putExtra("units2", valCH2.units);     // key, value pair

intent.putExtra("value2", valCH2.value);

intent.putExtra("units3", val.units);     // key, value pair for MATH

intent.putExtra("value3", val.value);

intent.setAction("com.mooshim.mooshimeter.CH");

2.  BroadcastIntentData used to be called by devices.MooshimeterDevice and devices.Legacy.MooshimeterDevice.  However, this technique would only broadcast CH1 and CH2, but not MATH.  So, now BroadcastIntentData is called from activities.DeviceActivity which will send all three channels, including MATH.

3.  In activities.DeviceActivity, I noticed there was logic in onPause to allow speech to run in background.  So, I add the same logic for broadcast intents to run in background.

Some Notes:  To use these features, you have two choices.  Either download the open source beta34 from here  https://github.com/DuaneOne/Mooshimeter-AndroidApp-34beta-BcastIntent and compile for yourself.    Or you can just download the .apk file and side-load it onto your android device.   I used Android Studio to change the 4 files identified above and added comments that begin with 8Feb2017.  

Turn on the global setting in the android app for broadcast intents.   The global setting page is the same page used to set temperature readings to C or F. 

Set the receiver action to “com.mooshim.mooshimeter.CH”.   There may be many intents sent by other apps on the phone and this acts as a first level filter for the receiver.  

Once the receiver gets the intent, you can utilize any or all of the six variables (extras) as key:value pairs.  The keys are “value1”, “units1”,  “value2”, “units2”,  “value3”, “units3”  and the values are meter readings and units for the channel.  

Value1 is the top channel in the mooshimeter app display, value2 is the middle display, and value3 is the MATH channel display.  The values are in base units. A display reading of 5.13mV is sent as value 0.00513 or 5.13E-3 and units V.  And 8.245Kohm is sent as value 8245 and units is the Greek ohm symbol, which is unicode U+2126 or UTF-16(hex) 0x2126 or C++/Java source code “\u2126”.   The values can also be OUT OF RANGE or INVALID INPUTS (MATH).

The technique used to allow broadcast intents in background mode makes it a little more difficult to completely close the mooshimeter app.  After all, when you back out of the app or press the phone home button, you still want the app to be running for broadcast intents.   So how do you turn the app all the way off?  First, this is only an issue when the broadcast intent global flag is set.  If the flag is off, the app reverts to its original behavior.   Second, you can always use android OS settings/application manager (or Apps)/mooshimeter to force stop or just turn off bluetooth.  This is probably not the most elegant technique, but alas, it is the best I could do at the moment without adding an additional button for either background or exit which would take up precious real estate on the display.

I have read that the android OS is notorious for terminating background apps when it thinks it needs the resources, usually RAM.  I minimize the issue by not using my main phone since it has many other apps on it.  For the mooshimeter, I use a dedicated phone since they are so cheap, at least in my area of the USA.  I get a prepaid phone, any carrier, and never activate it.  Usually I see them for $20 or less on sale with android 4.4, or android 5 is better.  This makes an awesome “android development system”.  So far I have not needed root when I use this with the Tasker app.

Let me know how this works for you.  It would be great if we could finalize the naming for the intent action and data.  In the Mooshimeter Support Forum, I post (occasionally) under the name Duane.
