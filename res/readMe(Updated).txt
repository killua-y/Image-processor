To run the program please use the following syntax:
• Loading a photo into the program:   load imageLocation imageName
• Save an image:                      save fileSaveName imageName
• Horizontally Flip an image:         horizontal-flip imageName modifiedImageName
• Vertically Flip an image:           vertical-flip imageName modifiedImageName
• Brighten or Darken an image:        brighten IntegerIncrementAmount(Positive for brighten and
    negative for darken) imageName modifiedImageName
• Visualize the Red Component:        red-component imageName modifiedImageName
• Visualize the Green Component:      green-component imageName modifiedImageName
• Visualize the Blue Component:       blue-component imageName modifiedImageName
• Visualize the Value Component:      value-component imageName modifiedImageName
• Visualize the Intensity Component:  intensity-component imageName modifiedImageName
• Visualize the Luma Component:       luma-component imageName modifiedImageName
• Greyscale an image:                 greyscale imageName modifiedImageName
• Sepia Tone an Image:                sepia imageName modifiedImageName
• Blur an Image:                      blur imageName modifiedImageName
• Sharpen an Image:                   sharpen imageName modifiedImageName

Instructions:
Please load an image first by following the load syntax, and then you can modify it
however you please!
Citation for images provided: The images provided we created using https://www.pixilart.com/".

Program Design:

-----------------------------------------------Model-----------------------------------------------
IImageProcess - This interface contains all the operations that the program is capable of in order
    to modify the image based on whatever user input is passed to it.

ImageProcessImpl - This class is implementing the IImageProcess interface, and it contains the
    implementations of each form of modification to images. The controller will communicate with
    the model and this class will modify the image selected by the user. This class uses Pixel and
    Image objects which we have separate classes that define each and what they're capable of.

Image - The Image class defines what an Image actually is inside our program. It is made up of a 2d
    array of Pixels in order for us to modify the images pixel by pixel for each different function.

Pixel - The Pixel class represents a singular pixel inside the array of pixels that make up an
    image. The Pixel class implements how each different function should affect the individual
    pixel. For example, the red-component visualization should modify the pixel by taking its red
    value and making it the same for the rest of the rgb values of that singular pixel.

-----------------------------------------------View-----------------------------------------------
IView - This is an interface that implements AView and represents any operations that should be 
offered by the view for this image processsing application. It contains only one method for 
rendering the message that will be sent to the command line to interact with the user.

AView - This is an abstract class for the view of the program. We made it an abstract class
in preperation for new features that have to be added to the program in future assignments.

ViewImpl - This class is the implementation of IView which represents the operations on an image
for the view portion of the image processing application.

BarChart - This class is for creating the histogram. It also contains a main method to showcase
that the histogram is working as intended.

IGuiView - This is an interface for our GUI which extends our previous interface IView since it
still uses the render method to communicate with the user.

GuiViewImpl - This class implements the IGuiView interface to create the implementation of the GUI
along with the visualization of the entire GUI.

EmptyView - This class represents an Empty GUI when the program is first run and is solely a
placeholder since it's empty.

--------------------------------------------Controller---------------------------------------------
IController - This is the interface for the controller of the program which also contains the
    methods for loading and saving files.

ControllerImpl - This is our implementation for IController which represents the controller for the
    program. The controller will be communicating with the view to talk to the user via the command
    line and will also manage loading and saving images. The controller also communicates with the
    model to modify the images based on the user's input on what to do to which image.

ICommand - This is an interface for a command which represents something the user can do to
    modify an image.

CommandImpl - This class acts as a parent class to all of our commands.

Brighten - This class extends the parent class for commands, CommandImpl, and implements the
    "brighten" command.

Flip - This class extends the parent class for commands, CommandImpl, and implements the
    horizontal and vertical flip commands.

Visualization - This class extends the parent class for commands, CommandImpl, and implements
    the red, green, blue, value, intensity, and luma component visualizations. Visualization also
    implements sepia and greyscale now.

Filter - This class extends the parent class for commands, CommandImpl, and implements the
           blur and sharpen command.

IGuiController - This is an interface for the controller specifically for the GUI side of the
program. It utilizes delegation to prevent code duplication and uses previously made methods
along with controlling the new GUI view.

GuiControllerImpl - This class implements our new interface IGuiController and will be
working with an IGuiView to control the GUI and work the program together as a whole.

-----------------------------------------Notable Changes HW 5---------------------------------------
1. Sepia and Greyscale were both implemented on the controller side through our previously made
    visualization class to prevent duplication. We added in-taking the cap value in our Pixel class
    as well to allow implementing these along with the other functions in the Visualization class
    all together instead of making a new class and duplicating code.
2. For Blur and Sharpen we made a new class in the controller called Filter which extends our
    parent command class CommandImpl similarly to all of our other commands. The reason we chose to
    make a new class was because the way Blur and Sharpen work is drastically different compared
    to any of the visualization functions due to its matrix multiplication.
3. To implement the Saving and Loading of new files, we created a helper for the ".jpg", ".png",
    and ".bmp".


-----------------------------------------Notable Changes HW 6---------------------------------------
1. How we implemented the new GUI in the Controller: Created a new interface for a new GUI controller 
which was called IGuiController and made a new class GuiControllerImpl that implemented it. We 
created an entirelynew interface for the GUI since it is drastically different from the old 
controller,but we utilized delegation to prevent code duplication and use old methods we developed 
previously.
2. To implement the GUI in the view portion we developed a new interface called IGuiView which
is made to display the entire view of the GUI. This includes the image panel, commands/functions
buttons to modify the image, the histogram, loading and saving file buttons, and text instructions
on how to use the program.
3. We were able to get our load to work with PPM files and display on the screen, but our save method
we weren't able to get working. 
4. We individually worked on our histogram and were able to make it fully functional. This was done
in a main method within the BarChart class. The histogram now also refreshes whenever a new image
is selected.
5. The other buttons like sharpen, blur, darken, brighten are not fully functional...
6. The main method was modified to enable accessing the GUI along with the old command line
interface.

CITATION FOR IMAGE:
Koala image provided by class CS3500.




