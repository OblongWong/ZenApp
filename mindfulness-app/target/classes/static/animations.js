// ZENAPP ANIMATION SYSTEM - Simple Version

var AnimationSystem = {

    showPointsAnimation: function(points, x, y) {
        // Create floating points element
        var pointsEl = document.createElement('div');
        pointsEl.className = 'floating-points';
        pointsEl.textContent = '+' + points + ' pts! 🎉';
        pointsEl.style.left = x + 'px';
        pointsEl.style.top = y + 'px';
        pointsEl.style.transform = 'translate(-50%, -50%)';

        document.body.appendChild(pointsEl);

        // Remove after animation completes
        setTimeout(function() {
            pointsEl.remove();
        }, 2000);

        // Show confetti
        AnimationSystem.showConfetti(x, y);
    },

    showConfetti: function(centerX, centerY) {
        var colors = ['#ff6b6b', '#4ecdc4', '#45b7d1', '#ffd93d', '#6bcf7f', '#a29bfe'];
        var confettiCount = 30;

        for (var i = 0; i < confettiCount; i++) {
            var confetti = document.createElement('div');
            confetti.className = 'confetti';

            // Random position around center
            var offsetX = (Math.random() - 0.5) * 200;
            var offsetY = (Math.random() - 0.5) * 200;

            confetti.style.left = (centerX + offsetX) + 'px';
            confetti.style.top = (centerY + offsetY) + 'px';
            confetti.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];
            confetti.style.animationDelay = (Math.random() * 0.3) + 's';
            confetti.style.animationDuration = (2 + Math.random()) + 's';

            document.body.appendChild(confetti);

            // Remove after animation
            setTimeout(function() {
                confetti.remove();
            }, 3500);
        }
    },

    showRipple: function(x, y) {
        var ripple = document.createElement('div');
        ripple.className = 'ripple-effect';
        ripple.style.left = x + 'px';
        ripple.style.top = y + 'px';
        ripple.style.transform = 'translate(-50%, -50%)';

        document.body.appendChild(ripple);

        setTimeout(function() {
            ripple.remove();
        }, 1000);
    },

    pulse: function(element) {
        element.classList.add('pulse-animation');
        setTimeout(function() {
            element.classList.remove('pulse-animation');
        }, 600);
    },

    shake: function(element) {
        element.classList.add('shake-animation');
        setTimeout(function() {
            element.classList.remove('shake-animation');
        }, 500);
    },

    bounce: function(element) {
        element.classList.add('bounce-animation');
        setTimeout(function() {
            element.classList.remove('bounce-animation');
        }, 1000);
    }
};

window.AnimationSystem = AnimationSystem;

console.log('✨ Animation System loaded successfully');