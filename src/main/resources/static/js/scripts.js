// Manage the visibility of the loading indicator
document.body.addEventListener("htmx:beforeRequest", function () {
  document.getElementById("loadingIndicator").classList.remove("hidden");
});

document.body.addEventListener("htmx:afterRequest", function () {
  document.getElementById("loadingIndicator").classList.add("hidden");
});

// Manage the visibility of the continue button
document.addEventListener("htmx:afterSwap", function (evt) {
  console.log("target: " + evt.detail.target);
  var currentDiv = evt.detail.target;
  var previousDiv = currentDiv.previousElementSibling;
  var nextDiv = currentDiv.nextElementSibling;
  var continueButton;

  if (currentDiv.id === "constraintsDiv") {
    continueButton = currentDiv.querySelector(".continueButton");

    if (continueButton) {
      continueButton.style.display = "none";
    }
  }

  if (previousDiv) {
    continueButton = previousDiv.querySelector(".continueButton");

    if (continueButton) {
      continueButton.style.display = "none";
    }
  }

  if (nextDiv) {
    continueButton = currentDiv.querySelector(".continueButton");

    if (nextDiv.innerHTML.trim() !== "") {
      if (continueButton) {
        continueButton.style.display = "none";
      }
    }
  }
});
