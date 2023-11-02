import {Buffer} from "buffer";

export const jswTokenFetch = async (endpoint, options = {}, methodPlusToken) => {
    console.log("method")

    const response =  await fetch(`http://localhost:8080${endpoint}`, {
        credentials: "include",
        headers: {
            "Authorization":  methodPlusToken,
            "Content-Type": "application/json" },
        ...options });
   /* if(endpoint === "/login"){
        localStorage.setItem('jwt', response);
    }*/
    console.log(response)
    return response;

}