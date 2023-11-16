import {LogginInContext} from "../main.jsx";
import {UserRoleContext} from "../main.jsx";
import {useEffect} from "react";
import {useContext} from "react";

const LogOut = () => {
    const {loggedIn, setLoggedIn} = useContext(LogginInContext);
    const {userRole, setUserRole} = useContext(UserRoleContext);
    useEffect(() => {
        setLoggedIn(false);
        setUserRole("USER");
        localStorage.clear();

    }, []);

    return (
        <div>
            <img height="auto" width="800px" src={"https://imgur.com/7MNlyvp.png"}/>
            <h3 style={{"position":"absolute",
            "top":"300px", "left":"100px", "fontStyle": "italic", "color":"red", "fontSize":"100px"
            , "textDecoration": "underline"}}>Danke</h3>
        </div>
    );
}

export default LogOut