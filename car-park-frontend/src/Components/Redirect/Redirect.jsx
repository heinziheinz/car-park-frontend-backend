import {useNavigate, useParams} from "react-router-dom";
import {useEffect} from "react";

const Redirect = () => {
    const {id} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        navigate(`/car-house-update/${id}`);
    }, []);

    return null;
}
export default Redirect;