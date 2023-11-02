import {LogginInContext} from "../main.jsx";
import {UserRoleContext} from "../main.jsx";
import {useEffect} from "react";
import {useContext} from "react";

const LogOut = () => {
    const {loggedIn, setLoggedIn} = useContext(LogginInContext);
    const {userRole, setUserRole} = useContext(UserRoleContext);
    useEffect(()=>{
        setLoggedIn(false);
        setUserRole("USER");
        localStorage.clear();

    }, []);

    return <h3>LogOut</h3>
}

export default LogOut