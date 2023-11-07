import {useState} from "react";
import {loadJson} from "../../Utilities/loadJson.js"
import Loading from "../Loading/Loading.jsx";

const CarHouseForm = ({carHouse, onSave}) => {
    console.log(carHouse?.houseName)
    console.log(carHouse?.address)
    console.log(carHouse?.capacity)
    const [houseName, setHouseName] = useState(carHouse?.houseName ?? "");
    const [address, setAddress] = useState(carHouse?.address ?? "");
    const [capacity, setCapacity] = useState(carHouse?.capacity?? "");
    console
    console.log("capacity")
    console.log(capacity)

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
export default CarHouseForm;