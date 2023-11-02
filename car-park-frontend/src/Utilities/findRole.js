export const findRole = (roleArray) => {
    if (roleArray.includes("ADMIN")) {
        return "ADMIN";
    } else {
        return "USER";
    }
}