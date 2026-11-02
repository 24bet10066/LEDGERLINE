document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll("form").forEach((form) => {
    form.addEventListener("submit", () => {
      form.querySelectorAll("button").forEach((button) => {
        button.disabled = true;
        button.textContent = "Loading…";
      });
    });
  });
  document.querySelectorAll("[data-loading]").forEach((element) => {
    element.hidden = true;
  });
});
