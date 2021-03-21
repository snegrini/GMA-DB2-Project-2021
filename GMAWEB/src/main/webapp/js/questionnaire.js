(function() { // Avoid variables ending up in the global scope

    window.onload = function() { // Wait for the document to finish loading
        document.getElementById("nextBtn").addEventListener("click", nextBtnClicked, false);
        document.getElementById("prevBtn").addEventListener("click", prevBtnClicked, false);
    }

    function nextBtnClicked() {
        // TODO hide mandatory div and show optional div
    }

    function prevBtnClicked() {
        // TODO hide optional div and show mandatory div
    }

})();