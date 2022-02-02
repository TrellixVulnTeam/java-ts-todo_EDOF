import { useState } from 'react';
import { v4 as uuidv4 } from 'uuid';

import FormInput from './FormInput';
import './TodoAdder.styles.scss';

const TodoAdder = ({ getTodos }) => {
  const [inputValue, setInputValue] = useState('');

  const handleSubmit = async e => {
    e.preventDefault();
    await fetch('http://localhost:8080/api/todos', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        id: uuidv4(),
        text: inputValue,
        isComplete: false,
      }),
    });
    await getTodos();

    setInputValue('');
  };

  return (
    <form className='todo-adder' onSubmit={handleSubmit}>
      <FormInput
        type='text'
        name='add'
        value={inputValue}
        onChange={e => {
          setInputValue(e.target.value);
        }}
        label='Add a todo'
        required
      />
      <button className='todo-adder__button'>Submit</button>
    </form>
  );
};

export default TodoAdder;
