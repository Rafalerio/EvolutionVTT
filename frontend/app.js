const playMenu = document.querySelector("#play-menu");
const menuToggles = document.querySelectorAll(
  "#play-menu-toggle, .mobile-menu-toggle",
);

function setPlayMenuOpen(isOpen) {
  playMenu.classList.toggle("is-collapsed", !isOpen);
  menuToggles.forEach((toggle) => {
    toggle.setAttribute("aria-expanded", String(isOpen));
  });
}

menuToggles.forEach((toggle) => {
  toggle.addEventListener("click", () => {
    const isOpen = toggle.getAttribute("aria-expanded") === "true";
    setPlayMenuOpen(!isOpen);
  });
});

const toast = document.querySelector(".toast");
let toastTimeout;

function showNotice(message) {
  toast.textContent = message;
  toast.classList.add("is-visible");
  window.clearTimeout(toastTimeout);
  toastTimeout = window.setTimeout(() => {
    toast.classList.remove("is-visible");
  }, 2800);
}

document.querySelectorAll("[data-notice]").forEach((element) => {
  element.addEventListener("click", (event) => {
    event.preventDefault();
    showNotice(element.dataset.notice);
  });
});

document.querySelectorAll(".favorite-toggle").forEach((button) => {
  button.addEventListener("click", () => {
    const isFavorite = button.getAttribute("aria-pressed") === "true";
    const roomName = button.closest(".favorite-room").querySelector("h3").textContent;
    button.setAttribute("aria-pressed", String(!isFavorite));
    button.setAttribute(
      "aria-label",
      `${isFavorite ? "Add" : "Remove"} ${roomName} ${isFavorite ? "to" : "from"} favorites`,
    );
  });
});

document.querySelectorAll(".sidebar-link").forEach((link) => {
  link.addEventListener("click", () => {
    document.querySelectorAll(".sidebar-link").forEach((item) => {
      item.classList.remove("is-active");
      item.removeAttribute("aria-current");
    });
    link.classList.add("is-active");
    link.setAttribute("aria-current", "page");
  });
});
