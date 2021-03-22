(function() { // Avoid variables ending up in the global scope
    let questionNumber = 1;

    window.onload = function() { // Wait for the document to finish loading
        document.getElementById("addQuestionBtn").addEventListener("click", addQuestionField, false);
        document.getElementById("removeQuestionBtn").addEventListener("click", removeQuestionField, false);
    }

    function addQuestionField() {
        let questions = document.getElementById("questions");

        questionNumber++;

        // Create <br> tag
        let lineBreak = document.createElement("br");

        // Create new text label
        let questionLabel = document.createElement("label");
        questionLabel.setAttribute("for", "question" + questionNumber);
        questionLabel.textContent = "Question " + questionNumber + ": ";

        // Create new input field
        let questionField = document.createElement("input");
        questionField.setAttribute("type", "text");
        questionField.setAttribute("name", "question[]");
        questionField.setAttribute("id", "question" + questionNumber);
        questionField.setAttribute("size", "45");
        questionField.required = true;

        questions.appendChild(lineBreak);
        questions.appendChild(lineBreak.cloneNode());
        questions.appendChild(questionLabel);
        questions.appendChild(questionField);
    }

    function removeQuestionField() {
        if (questionNumber > 1) {
            let questions = document.getElementById("questions");

            questionNumber--;

            // One for the Input, one for the input and two for the br
            questions.removeChild(questions.lastChild);
            questions.removeChild(questions.lastChild);
            questions.removeChild(questions.lastChild);
            questions.removeChild(questions.lastChild);
        }
    }
})();