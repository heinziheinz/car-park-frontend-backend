import {useEffect} from "react";

const FetchUser = () => {

    useEffect(() => {
        fetchUser().then(data => {

        })
            .catch(error => {
                console.error('Fehler beim Abrufen der Benutzerdaten:', error);
            });
    }, []);

    const fetchUser = async () => {

    };

    return (
        <h2>Fetch User</h2>
    );
}

export default FetchUser;