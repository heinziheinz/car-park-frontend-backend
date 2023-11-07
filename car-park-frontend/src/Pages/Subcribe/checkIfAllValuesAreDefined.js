export const checkIfAllValuesAreDefined = (object) => {
    let Boolean = true;
    for (const property in object) {
        if(object[property].length === 0){
            Boolean = false;
        }
    }
    return Boolean;
}