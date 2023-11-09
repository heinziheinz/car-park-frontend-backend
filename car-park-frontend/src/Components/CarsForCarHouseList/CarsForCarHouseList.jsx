const CarsForCarHouseList = ({cars,valueForActionButton,actionHandler}) => {
    return (
        <div className="EmployeeTable">
            <table>
                <thead>
                <tr>
                    <th name="name">name</th>
                    <th name="type">price</th>
                </tr>
                </thead>
                <tbody>
                {cars.map((car) => (
                    <tr key={car.id}>
                        <td>{car.typeName}</td>
                        <td>{car.price}</td>
                        <td><img src={car.image} width="200" height="100"/></td>
                        <td>
                            <button type="button" onClick={() => actionHandler(car.id)}>
                                {valueForActionButton}
                            </button>
                        </td>

                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
}

export default CarsForCarHouseList;