import {useState} from "react";

const ReservationForm = ({reservation, onSave}) => {
    console.log(reservation)
    const [typeName, setTypeName] = useState(reservation?.car.typeName ?? "");
    const [startDate, setStartDate] = useState(reservation?.startDate ?? "");
    const [endDate, setEndDate] = useState(reservation?.endDate ?? "");

    const onSubmit = (e) => {
        e.preventDefault();
        return onSave({typeName, startDate, endDate});

    }

    return (
        <form className="ReservationForm" onSubmit={onSubmit}>
            <div className="control">
                <label htmlFor="first-name">Car:</label>
                <input
                    value={typeName}
                    onChange={(e) => setTypeName(e.target.value)}
                    name="type-name"
                    id="type-name"
                />
                <label htmlFor="price">Start Date:</label>
                <input
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                    name="start-date"
                    id="start-date"
                    type="date"
                />
                <label htmlFor="url-link">End Date:</label>
                <input
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                    name="end-date"
                    id="end-date"
                    type="date"
                />
            </div>
            <input type="submit" value={"Push"}/>
        </form>
    );
}
export default ReservationForm;