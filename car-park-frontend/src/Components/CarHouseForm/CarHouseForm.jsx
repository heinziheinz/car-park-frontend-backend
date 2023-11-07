import {useState} from "react";
import {loadJson} from "../../Utilities/loadJson.js"
import Loading from "../Loading/Loading.jsx";

const CarForm = ({carHouse, onSave}) => {
    const [houseName, setHouseName] = useState(carHouse?.typeName ?? "");
    const [address, setAddress] = useState(carHouse?.price ?? "");
    const [capacity, setCapacity] = useState(carHouse?.image ?? "");

    const onSubmit = (e) => {
        e.preventDefault();
        return onSave({houseName, address, capacity});

    }

    return (
        <form className="CarHouseForm" onSubmit={onSubmit}>
            <div className="control">
                <label htmlFor="first-name">CarHouse Name:</label>
                <input
                    value={houseName}
                    onChange={(e) => setHouseName(e.target.value)}
                    name="carhouse-name"
                    id="carhouse-name"
                />
                <label htmlFor="price">Address:</label>
                <input
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    name="address"
                    id="address"
                />
                <label htmlFor="url-link">Capacity:</label>
                <input
                    value={capacity}
                    onChange={(e) => setCapacity(e.target.value)}
                    name="capacity"
                    id="capacity"
                />
            </div>
            <input type="submit" value={"Push"}/>
        </form>
    );

}
export default CarForm;