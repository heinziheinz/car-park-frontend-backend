const SelectElement=({elementArray, onChangeHandler, defaultValue, name})=>{
    return (
        <div>
            <label htmlFor={defaultValue}>defaultValue</label>
            <select className="select" id={name} name={name} onChange={onChangeHandler} defaultValue="">
                <option value="" disabled>{defaultValue}</option>
                {elementArray.map((value, index) => {
                    return <option key={index} value={value}>{value}</option>;
                })}
            </select>
        </div>
    );
}
export default SelectElement;