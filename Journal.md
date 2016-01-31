# Journal #
## 15 puzzle ##

### May 16 – May 23 ###

Added the portrait/ landscape puzzle view depend in which mode the picture was taken
Added shadow to the label of the image

#### Lesson learned: ####
I learned how to implement shade

#### Planned next week: ####
GAME OVER!!!!

#### Planned next month :-)) ####
Implement the changed for different device and upload the application to the Google play.

### May 9 – May 16 ###

  * Added the game menu
  * Numbers – Add/Cancel Tilt
  * Picture/Photo – Add/Cancel Tilt and Add/Cancel Hint
  * Added shaking sound
  * Added screen on while playing the game.

#### Problems: ####
Tried to do a toggled menu, however couldn’t find any option like this. So I used the
`setVisible(true/false)` to create the effect of the toggling.
The drawing of the hint numbers on the chopped picture was also challenging. The easy part was to draw the numbers but to clear them was kind of tricky.
The playing the shaking sound wasn’t hard, but it start working only after I set a delay of 200msec while playing the sound. Otherwise the sound couldn’t be heard. The start and stop occurred to fast for the sound.

#### Lesson learned: ####
How is to work with the menu. How is to load the mp3 sound file.
And how is to keep the screen on.

#### Planned next week: ####
Finalize and close the application.

### May 2 – May 9 ###

  * Added the feature of loading, chopping and make a puzzle from the device gallery.
  * Added feature of taking a photo and making the puzzle out of it without launching the gallery.

#### Problems: ####
> The capturing the image through the camera is working through the

```
  Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
  startActivityForResult(cameraIntent, 0);
```

> This should bring the picture to our onActivityResult function that should be the in Intent data.
> (Without any problems same thing work very well with the gallery picture) however the data that I get from the intent was null. I read a lot of android documentation and understand     that this could happened because of the:

```
  cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
```

> Since I wanted to bring a high quality picture to the game and need to look for a picture in provided URI. However removing of this line, and suggestion to get a low-resolution image   didn’t work, I still got null in the data variable of the result function.
> I solved the problem using a slightly different coding

```
  mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); 
```

> I saved the path that I save in the picture and then putExtra previously saved URI
```
  Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);  
  startActivityForResult(cameraIntent, CAPTURE_PICTURE_INTENT);
```
It magically worked ☺

#### Lesson learned: ####
  * How does the result intent working? Using of the camera and the gallery through intent.
  * Cursor query, that this interface provides random read-write access to the result set returned by a database query. With this interface I get the picture from the current path.

#### Planned next week: ####
> To add sound to the game

### April 25 – May 2 ###

Added the accelerometer movement of the numbers. The new code was added to the previously implicated Shake method that uses the accelerometer as well. However now upon the tilt the different interface is called.

#### Problems: ####
  * The hardest part was to implement the filter of the accelerometer data.Since there is a lot of data coming, and I shouldn’t change the position of the square
> > at each movement but only when I have a real movement.
  * I used the hysteresis filter. I realized that when I want the number to move is when the accelerometer value is +/-3 on x, y-axis however nothing with move when the coming data          toggling between 3.5 and 2.5 the only way to move is to reach the stable condition of +/-1 on x, y-axis.

#### Lesson Learned: ####

> Without the hysteresis filter the movement of the puzzle is unstable.
> ![https://code.google.com/p/my-15-puzzle-game/source/browse/wiki/hysteresis.png](https://code.google.com/p/my-15-puzzle-game/source/browse/wiki/hysteresis.png)

#### Planned next week: ####
> Load the picture from the gallery, split it up to the squares and make a puzzle.


### April 18 – April 25 ###

Finished the animation.
The first attempt to animation was very slow and crashes a lot.
The hard part was to understand do to redraw on canvas then the calculation of the new position was already easy. Since the decoding of the bitmaps was done in `onCreate()` and the movement steps calculations I decided to do in different function, the doDraw function started to be very simple base function that only draw elements. It is working very fast without and slow motion problems.

#### Lesson learned: ####
The lesson is to do all the decoding of the bitmaps to do before drawing them on canvas. The decoding slow downs the drawing. All the decoding should be done on `onCreate()` method Tried to avoid unclear “spaghetti code”
Be focused on the code but also see the all-abstract picture.

##### Planned next week. #####
  * Accelerometer movement.
  * To make a code more efficient, avoid a mess work.


### April 11 – April 18 ###

Made a small projects involves `class SurfaceView`.
Tried to understand how to draw several images on canvas at the same time.

##### Problems: #####
> No problems at all since the animation was done on the singe object and was drawn into the lock/unlock canvas area.

##### Lesson Learned: #####
> The Animation could work through the `class SurfaceView`, however it would cost a lot of CPU.

##### Planned next week. #####
> Finally implement the bitmaps animation using the ` class SurfaceView`.


### April 4 – April 11 ###

Created a new GameView class that draw the image on the screen. Remove all the view part from the Activity.
Tried to do the animation of the moving numbers on the canvas.
Implemented the “shake” event.


##### Problems: #####
> Tried to use the Translate Animation and frame animation. Both were not a good fit for the application. `TranslateAnimation` from the `onTouchEvent` eventually created the animation however for the all canvas and not the specific number.
In the shake implementation was hard to understand the accelerometer logic callback function and how to filter the noise and connect the module to activity.

##### Lesson Learned: #####
> Better not to use `frameAnimation` and `Translate Animation` to implement different non-sequential images on the canvas.

##### Planned next week. #####
> Instead of the View class switch to the SurfaceView and then add animation to the canvas.


### March 28- April 4 ###

Implementation of low pass filter of the gyroscope data.

##### Problems: #####
> How to redraw the canvas after gyroscope evaluation.

##### Planned next week. #####
> Animation of the changing images.

### March 21- March 28 ###
Algorithms behind the view:
  * Field of the game
  * The movement of the selected cell if possible
  * Mixed field
  * Normal field
This part was just a java coding without much unique code for android.

The game View:
  * Loading the images from the res folder and draw them according to their position on the field. Both, in portrait and the landscape mode.
  * The implementation of the touch event and image movement according to the user finger position,
  * Adding message dialog to the user at the end of the game.
  * Start using gyroscope.

##### Problems: #####
  * The loading of the multiple images from the res folder is not intuitive.
  * Dynamically locate the images on the screen while using only the image and screen resolution. Convert the actual finger position to the field position.
  * Redraw the images on the screen after the finger touch.
  * Gyroscope data evaluation, landscape

##### Lesson Learned: #####
  * It is better to use the assets folder for loading the images.
  * For redraw the images it is better to use `onTouch()` instead `onTouchEvent()` since the first on has an option of the view parameters that can use postInavlidate to redraw the current view.
  * The starting a new game after game over should first close the current activity and then open the new game through intent.
  * The Gyroscope data should be filtered.
##### Planned next week. #####
> Gyroscope data evaluation, animation of the changing images.

### March 14- March 21 ###
  * Implementation of the first UI.

##### Problems: #####
  * The location of the fifteen bmp.
  * The draw of the bmp in correct location.
##### Lessons learned: #####
  * The numbers class design is not efficient for the game implementation
##### Planned next week: #####
Change the initial game implementation into more intuitive design.






















