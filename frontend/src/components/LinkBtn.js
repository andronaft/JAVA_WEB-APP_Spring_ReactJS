import React from 'react'
import PropTypes from 'prop-types'
import { Link } from './Router'

const LinkBtn = ({ to, label }) => {
  return (
    <Link to={to}>
        {label}
    </Link>
  )
}

LinkBtn.propTypes = {
  to: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
}

export default LinkBtn