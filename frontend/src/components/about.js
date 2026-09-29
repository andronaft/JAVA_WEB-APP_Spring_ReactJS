import React from "react";

function getTwoDecimal(num) {
  return parseFloat(num.toFixed(2) + 0.5);
}

const mouse = {
  decimal(coord) {
    return getTwoDecimal(coord / 1000);
  },
  x(e) {
    return Math.abs(e.clientX - (window.innerWidth / 2));
  },
  y(e) {
    return Math.abs(e.clientY - (window.innerHeight / 2));
  }
};

const changeTextAlphaVal = (txt, e) => {
  const root = document.querySelector(":root");
  const cssVar = "--alpha";
  const currentAlpha = getComputedStyle(root)
    .getPropertyValue(cssVar)
    .trim();

  const max = parseFloat(currentAlpha);
  const dx = mouse.decimal(mouse.x(e));
  const dy = mouse.decimal(mouse.y(e));

  let alphaVal;
  if (dx <= 0) {
    alphaVal = dy >= max ? dy : getTwoDecimal(max - dy);
  } else {
    alphaVal = dx >= max ? dx : getTwoDecimal(max - dx);
  }

  txt.style.setProperty(cssVar, alphaVal);
};

function createShadow(e, currTarget) {
  const walk = Math.round(Math.max(window.innerWidth, window.innerHeight) / 6);
  const coordWalk = (coord, side) => Math.round(coord / side * walk - walk / 2);
  const xWalk = coordWalk(e.clientX, currTarget.offsetWidth);
  const yWalk = coordWalk(e.clientY, currTarget.offsetHeight);

  const pink = [255, 0, 139];
  const blue = [0, 86, 255];
  const yellow = [255, 240, 0];
  const typoAlpha = 0.6;

  const typo = currTarget.querySelector(".typo");
  changeTextAlphaVal(typo, e);

  typo.style.textShadow = `
    ${xWalk}px ${yWalk}px 0 rgba(${pink}, ${typoAlpha}),
    ${xWalk * -1}px ${yWalk * 2}px 0 rgba(${blue}, ${typoAlpha}),
    ${xWalk * -2}px ${yWalk * -1}px 0 rgba(${yellow}, ${typoAlpha})
  `;
}

// The mouse effect used to be attached in App.componentDidMount, so it only
// worked when the page was opened directly on /about. Now it lives here.
class About extends React.Component {
  onMouseMove = e => createShadow(e, e.currentTarget);

  onTouchMove = e => createShadow(e.changedTouches[0], e.currentTarget);

  render() {
    return (
      <div className="jumbotron zukabout">
        <div className="heading" onMouseMove={this.onMouseMove} onTouchMove={this.onTouchMove}>
          <h1 className="typo" contentEditable suppressContentEditableWarning spellCheck={false}>by Zuk</h1>
        </div>
      </div>
    );
  }
}

export default About;
