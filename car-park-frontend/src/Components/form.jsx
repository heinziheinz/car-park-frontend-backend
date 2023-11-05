import InputField from "./InputField.jsx";
import SubmitButton from "./SubmitButton.jsx";

const Form = ({handleSubmit, inputFields, onChangeHandler, children, disabled}) => {
    console.log(children)
    return (
        <form onSubmit={handleSubmit}>
            {children}
            {
                inputFields.map((fields, index) => {
                    return (
                        <div key={"wrapper" + index}>
                            <InputField
                                key={index}
                                name={fields.name}
                                className={fields.className}
                                label={fields.label}
                                type={fields.type}
                                placeholder={fields.placeholder}
                                onChangeHandler={onChangeHandler}
                                value={fields.value}
                            />
                        </div>
                    );
                })
            }
            <SubmitButton value={"Submit"} disabled={disabled}/>
        </form>
    );
}
export default Form;