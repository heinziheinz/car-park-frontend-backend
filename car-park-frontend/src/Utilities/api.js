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

export const jwtTokenFetch = async (endpoint, options = {}, headers) => {
    console.log("method")
    console.log(import.meta.env.VITE_BACKEND_URL + endpoint)
    console.log(headers)


    const response = await fetch(import.meta.env.VITE_BACKEND_URL + endpoint, {
        credentials: "include",
        headers: headers,
        ...options
    });
    /* if(endpoint === "/login"){
         localStorage.setItem('jwt', response);
     }*/
    console.log("HHHHHHHHHHH")
    console.log(response)
    return response;

}
