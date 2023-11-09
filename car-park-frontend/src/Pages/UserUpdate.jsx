import {useEffect, useState} from "react";
import {fetchAuthenticated} from "../Utilities/api.js";
import {useParams, useNavigate} from "react-router-dom";
import Loading from "../Components/Loading/Loading.jsx";
import Form from "../Components/Form/Form.jsx";

const UserUpdate = () => {
    const {id} = useParams();
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        (async () => {
            try {
                //http://localhost:8080/users/id/3
                const userToBeUpdated = fetchAuthenticated(`/users/id/${id}`, {
                    method: "GET",
                })
                if (userToBeUpdated.ok) {
                    const userToBeUpdatedParsed = await userToBeUpdated.json();
                    setUser(userToBeUpdatedParsed);
                    setLoading(false);
                }
            } catch (err) {

            }
        })();
    }, []);

    const handeSubmit = () => {

    }
    const onChangeHandler = () => {

    }
    const inputFields=[];
    const disabled= true;

    if (loading) {
        return <Loading/>;
    }
    //({handleSubmit, inputFields, onChangeHandler, children, disabled})
    //TODO: Hier weiter
    return <Form
        handleSubmit={handeSubmit()}
        inputFields={inputFields}
        onChangeHandler={onChangeHandler}
        disabled={disabled}
    />;
}
export default UserUpdate;