"use strict";

document.addEventListener('DOMContentLoaded', () => {

    /**
     * Soumette le formulaire de la l'appui de la touche 'Enter'.
     * Doit être lié à l'événement 'keypress'.
     * @param {String} btnSubmitId Identifiant du bouton
     * de validation du formulaire
     * @param {Object} event Événement du clavier
     * @returns false
     */
    window.submitOnEnter = (event, btnSubmitId) => {
        if (event.keyCode === 13) {
            document.getElementById(btnSubmitId).click();
        }
        return false;
    };
});

