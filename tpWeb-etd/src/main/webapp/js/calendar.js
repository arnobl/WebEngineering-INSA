"use strict";

// Helpers

function getAnnee(xmlDoc) {
    return xmlDoc.getElementsByTagName("matiere")[0].getElementsByTagName("annee")[0].textContent;
}

function getMatiere(xmlDoc) {
    return xmlDoc.getElementsByTagName("matiere")[0].getElementsByTagName("name")[0].textContent;
}

function getHoraire(xmlDoc) {
    return new Date(xmlDoc.getElementsByTagName("horaire")[0].textContent);
}

function getDuree(xmlDoc) {
    return xmlDoc.getElementsByTagName("duration")[0].textContent;
}

function getEns(xmlDoc) {
    let ens = xmlDoc.getElementsByTagName("ens")[0];
    return ens.getElementsByTagName("name")[0].textContent;
}

document.getElementById('loadCalendar').onclick = function () {
    let week = document.getElementById('semaine').value;
    let idRes = document.getElementById('ressource').value;

    //TODO
};


