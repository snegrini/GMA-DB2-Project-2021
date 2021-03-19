(function() { // Avoid variables ending up in the global scope

    let incrementCounter = (function () {
        let questionCounter = 1;

        return function() {
            questionCounter += 1;
            return questionCounter;
        }
    })();

    window.onload = function() { // Wait for the document to finish loading
        document.getElementById("addQuestionBtn").addEventListener("click", addQuestionField, false);
        //document.getElementById("removeQuestionBtn").addEventListener("click", removeQuestionField, false);
    }

    function addQuestionField() {
        let questions = document.getElementById("questions");

        let questionNumber = incrementCounter();

        // Create <br> tag
        let lineBreak = document.createElement("br");

        // Create new text label
        let questionLabel = document.createElement("label");
        questionLabel.setAttribute("for", "question" + questionNumber);
        questionLabel.textContent = "Question " + questionNumber + ": ";

        // Create new input field
        let questionField = document.createElement("input");
        questionField.setAttribute("name", "question[]");
        questionField.setAttribute("id", "question" + questionNumber);
        questionField.setAttribute("size", 45);

        questions.appendChild(lineBreak);
        questions.appendChild(lineBreak);
        questions.appendChild(questionLabel);
        questions.appendChild(questionField);
    }

    /*function removeQuestionField() {
        let questions = document.getElementById("questions");

        let questionNumber = decrementCounter();

    }*/
})();