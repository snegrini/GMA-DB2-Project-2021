(function() { // Avoid variables ending up in the global scope

    let questionNumber = createCounter("questions");

    window.onload = function() { // Wait for the document to finish loading
        document.getElementById("addQuestionBtn").addEventListener("click", addQuestionField, false);
        document.getElementById("removeQuestionBtn").addEventListener("click", removeQuestionField, false);
    }

    function addQuestionField() {
        let questions = document.getElementById("questions");

        questionNumber.increment();

        // Create <br> tag
        let lineBreak = document.createElement("br");

        // Create new text label
        let questionLabel = document.createElement("label");
        questionLabel.setAttribute("for", "question" + questionNumber.value());
        questionLabel.textContent = "Question " + questionNumber.value() + ": ";

        // Create new input field
        let questionField = document.createElement("input");
        questionField.setAttribute("type", "text");
        questionField.setAttribute("name", "question[]");
        questionField.setAttribute("id", "question" + questionNumber.value());
        questionField.setAttribute("size", "45");
        questionField.required = true;

        questions.appendChild(lineBreak);
        questions.appendChild(lineBreak.cloneNode());
        questions.appendChild(questionLabel);
        questions.appendChild(questionField);
    }

    function removeQuestionField() {
        if (questionNumber.value() > 1) {
            let questions = document.getElementById("questions");

            questionNumber.decrement();

            // One for the label, one for the input and two for the br
            questions.removeChild(questions.lastChild);
            questions.removeChild(questions.lastChild);
            questions.removeChild(questions.lastChild);
            questions.removeChild(questions.lastChild);
        }
    }

    function createCounter(counterName) {
        let counter = 1;

        // ONLY FOR DEBUG PURPOSE.
        /*function display() {
            console.log("Number of " + counterName + ": " + counter);
        }*/

        function increment() {
            ++counter;
            //display();
        }

        function decrement() {
            --counter;
            //display();
        }

        return {
            increment : increment,
            decrement : decrement,
            value : () => counter // Same as function() { return counter; }
        };
    }
})();