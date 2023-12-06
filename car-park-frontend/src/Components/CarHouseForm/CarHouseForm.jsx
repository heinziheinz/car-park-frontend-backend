import {useState} from "react";
const CarHouseForm = ({carHouse, onSave, children}) => {
    console.log(carHouse?.houseName)
    console.log(carHouse?.address)
    console.log(carHouse?.capacity)
    const [houseName, setHouseName] = useState(carHouse?.houseName ?? "");
    const [address, setAddress] = useState(carHouse?.address ?? "");
    const [capacity, setCapacity] = useState(carHouse?.capacity ?? "");
    // const [carsInCarHouse, setCarsInCarHouse] = useState(carHouse?.cars ?? []);

    console.log("capacity")
    console.log(capacity)

    const onSubmit = (e) => {
        e.preventDefault();
        return onSave({houseName, address, capacity});

    }

    return (
        <div className="carhouseWrapper">

            <form className="styleForm" onSubmit={onSubmit}>
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
            <div style={{display: 'flex'}}>
                {children.map((child, index) => (
                    <div key={index} style={{margin: '10px'}}>
                        {child}
                    </div>
                ))}
            </div>
        </div>
    );

}
export default CarHouseForm;