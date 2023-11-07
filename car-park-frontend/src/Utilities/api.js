export const fetchAuthenticated = async (endpoint, options) => {

    const userData = JSON.parse(localStorage.getItem("userdata"));

    return await fetch(import.meta.env.VITE_BACKEND_URL + endpoint, {
        credentials: "include",
        ...options,
        headers: {
            "Authorization": `Bearer ${userData.jwt}`,
            "Content-Type": "application/json",
            ...options.headers
        },
    });

}




