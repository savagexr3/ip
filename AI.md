# AI-Assisted Development (A-AiAssisted)
## Reflection on My Use of AI Tools
Throughout this project, I used AI tools such as ChatGPT and GitHub Copilot to enhance my development process. Rather than using AI to replace my thinking, I used it as a support tool to guide decisions, refine ideas, and improve efficiency. This experience changed how I view AI — not as a shortcut, but as a collaborator that still requires critical evaluation.
1. ### Learning JavaFX UI Through AI Guidance
   One of my biggest challenges was working with JavaFX UI. I did not have prior experience with JavaFX, and my initial instinct was to approach it similarly to HTML and CSS. However, I quickly realized that JavaFX does not provide tools like “inspect element,” which made layout debugging more difficult.
   I used AI to better understand how JavaFX layouts work, especially when trying to place the send button inside the rectangular text input box. While AI did not always give a perfect solution immediately, iterative prompting helped refine the implementation. Through this process, I learned more about JavaFX layout structures rather than blindly copying code.
   This experience taught me that AI is most effective when used interactively — providing context, testing the solution, and refining it step by step.
2. ### Documentation and Code Clarity
   For JavaDoc documentation, I used AI to generate method and class comments. Writing documentation manually for every method can feel repetitive, and AI helped reduce that burden.
   However, I noticed that AI-generated documentation sometimes missed standard JavaDoc conventions or lacked precision. This required me to review, edit, and sometimes rewrite parts of the documentation. Through this, I realized that documentation is not just about filling space — it requires clarity and accuracy, especially when explaining design intent.
   AI made the process faster, but I was still responsible for ensuring correctness and quality.
3. ### Understanding Software Architecture and Separation of Concerns
   AI also played a role in helping me think about project structure and code organization. For example, when implementing the FreeCommand, I questioned whether its logic should be placed inside TaskList. AI explained why that would violate separation of concerns, since TaskList should only manage tasks rather than handle command logic.
   This reinforced architectural principles that I learned in class. Instead of simply following instructions, I was able to reason about why certain design decisions were better.
   Similarly, when structuring individual command classes, I consulted AI to ensure the design would scale when adding new commands in the future. This helped me move from writing code that “just works” to writing code that is maintainable and extensible.
4. ### Debugging and Critical Evaluation of AI Suggestions
   One of the most challenging issues I faced involved sound effects. The sound worked correctly in my local IntelliJ environment but behaved incorrectly when exported as a JAR file, repeating multiple times unexpectedly.
   Initially, I used a different audio library. Based on AI’s suggestion, I switched to JavaFX’s MediaPlayer. Although AI suggested the alternative, I still had to understand the lifecycle and usage of the media player to implement it correctly. This experience showed me that AI can suggest solutions, but it cannot fully replace debugging skills and understanding of runtime behavior.
   ## Overall Reflection
1. Using AI significantly improved my productivity. Tasks that might have taken an entire day per week were reduced to roughly six hours. However, I also realized that efficiency gains come with responsibility. 
2. If I simply copied and pasted AI-generated code without understanding it, I would risk introducing bugs or architectural issues. Therefore, I made it a habit to:
   - Provide full context when prompting AI
   - Read and understand every generated solution
   - Modify and adapt suggestions to fit my project design 
3. Through this project, I learned that AI is not a substitute for understanding. Instead, it is a powerful assistant that enhances learning when used critically and responsibly.
   
4. Overall, AI helped me develop not only faster, but also more thoughtfully.