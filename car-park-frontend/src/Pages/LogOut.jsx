import {LogginInContext} from "../main.jsx";
import {UserRoleContext} from "../main.jsx";
import {useEffect, useContext} from "react";
import {useNavigate} from "react-router-dom";
import {fetchAuthenticated} from "../Utilities/api.js";

const LogOut = () => {
    const {setLoggedIn} = useContext(LogginInContext);
    const {setUserRole} = useContext(UserRoleContext);
    const navigate = useNavigate();
    useEffect(() => {
        (async () => {
            try {
                const loggedOut = await fetchAuthenticated(`/user-logout`, {
                    method: "GET"
                });
                if (loggedOut.ok) {
                    setLoggedIn(false);
                    setUserRole("USER");
                    localStorage.clear();
                }
            } catch (err) {
                console.error(err);
            }
        })();


    }, []);

    return (
        <div>
            <img height="auto" width="800px" src={"https://imgur.com/7MNlyvp.png"}/>
            <h3 style={{
                "position": "absolute",
                "top": "300px", "left": "100px", "fontStyle": "italic", "color": "red", "fontSize": "100px"
                , "textDecoration": "underline"
            }}>Danke</h3>
        </div>
    );
}

export default LogOut