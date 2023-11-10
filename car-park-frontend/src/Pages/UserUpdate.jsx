import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../Utilities/api.js";
import {useParams, useNavigate,} from "react-router-dom";
import Loading from "../Components/Loading/Loading.jsx";
import UserForm from "../Components/UserForm/UserForm.jsx";

const UserUpdate = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const [user, setUser] = useState(null);
    const [authorities, setAuthorities] = useState([]);
    const [loading, setLoading] = useState(true);
    const finalUser = {};

    useEffect(() => {
        (async () => {
            try {
                setLoading(true);
                //http://localhost:8080/users/id/3
                const userToBeUpdated = await fetchAuthenticated(`/users/id/${id}`, {
                    method: "GET",
                })
                console.log(userToBeUpdated);
                if (userToBeUpdated.ok) {
                    const userToBeUpdatedParsed = await userToBeUpdated.json();
                    console.log(userToBeUpdatedParsed)
                    setUser(userToBeUpdatedParsed);
                } else {
                    throw new Error("CarHouseError");
                }

                //http://localhost:8080/authorities

                const authorities = await fetchAuthenticated(`/authorities`, {
                    method: "GET",
                })
                console.log(authorities);
                if (authorities.ok) {
                    const authoritiesParsed = await authorities.json();
                    console.log(authoritiesParsed)
                    setAuthorities(authoritiesParsed);
                    setLoading(false);
                } else {
                    throw new Error("CarHouseError");
                }
            } catch (err) {
                console.error(err);
            }
        })();
    }, []);

    const handeSubmit = async (user) => {
        console.log(user)
        let authorities;
        if (user?.authority === "USER") {
            authorities = ["USER"];
        } else if (user?.authority === "ADMIN") {
            authorities = ["USER", "ADMIN"];
        } else {
            authorities = ["USER"];
        }
        user.authorities = authorities;
        console.log("myUser")
        console.log(user)

        try {
            setLoading(true);
            //http://localhost:8080/users/id/3
            const updatedUser = await fetchAuthenticated(`/users/${id}`, {
                method: "PUT",
                body: JSON.stringify(user)
            })
            console.log(updatedUser);
            if (updatedUser.ok) {
                const updatedUserParsed = await updatedUser.json();
                setUser(updatedUserParsed);
                setLoading(false);
                navigate("/show-all-users")
            }
        } catch (err) {
            console.error(err);
        }
    }

    if (loading) {
        return <Loading/>;
    }
    return <UserForm
        user={user}
        authorities={authorities}
        onSave={handeSubmit}
    />
}
export default UserUpdate;