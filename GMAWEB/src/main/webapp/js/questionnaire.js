(function() { // Avoid variables ending up in the global scope

    window.onload = function() { // Wait for the document to finish loading
        document.getElementById("nextBtn").addEventListener("click", nextBtnClicked, false);
        document.getElementById("prevBtn").addEventListener("click", prevBtnClicked, false);

        document.getElementById("optional-questions").classList.add("is-hidden");
    }

    function nextBtnClicked() {
        let answerInputList = document.getElementsByName("answer[]");
        let fieldSetValid = true;

        answerInputList.forEach(answer => {
            if (!answer.checkValidity()) {
                answer.reportValidity();
                fieldSetValid = false;
            }
        });

        if (fieldSetValid) {
            document.getElementById("mandatory-questions").classList.add("is-hidden");
            document.getElementById("optional-questions").classList.remove("is-hidden");
        }
    }

    function prevBtnClicked() {
        document.getElementById("mandatory-questions").classList.remove("is-hidden");
        document.getElementById("optional-questions").classList.add("is-hidden");
    }

})();