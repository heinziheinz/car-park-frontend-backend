export const loadJson = async (endpoint, options = {}) => {
    const response =  await fetch(`http://localhost:8080${endpoint}`, {
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        ...options });
    return await response.json();

}