# Text To Speech
A non-automated text-to-speech translator

## Acceptance Criteria (System Requirements)
1. Users been able to see text in a text box
2. Texts in the text area field are read-aloud by the system
3. Users should see a button to load text dat from local file
4. Users should have a call-to-action button that prompts the system to read the texts in the text box aloud.
5. System should allow user to load or type texts into the text area field.

## Analysis of System Requirements
1. The text box gives the user a visual representation of the texts loaded from local file or being typed by the user
2. Texts in the text box should be readable by the system i.e a UTF-8 encoded characters
3. A call-to-action button helps the system to load the file and populate the visual box, tune others settings like volume, speed, voice
4. Reading aloud of text is also prompted by a user-action

## Design and Implementation
The design of this project is a simple UI, with components:
- Label: Describing the project name
- Text area: For visual representation of the text or local file loaded into the system to read aloud
- Voice: action button for prompting the system on the voice to use in reading.
- Rate: action button for setting the speed of the speech
- Volume: action button, sets the volume of the device speaker
- System is implemented using `java`, `javafx` as the programming language and package 

## Challenges and insights
- Using `javafx` package have posed a significant challenge in my project implementation, `java` ecosystem is too large that finding packages might be a daunting task
