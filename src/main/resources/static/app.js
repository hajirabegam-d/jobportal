const jobsList = document.getElementById('jobsList');
const jobDetail = document.getElementById('jobDetail');
const jobForm = document.getElementById('jobForm');
const applicationForm = document.getElementById('applicationForm');
const reloadBtn = document.getElementById('reloadBtn');

let selectedJobId = null;

async function fetchJson(url, options = {}) {
  const response = await fetch(url, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });

  const contentType = response.headers.get('content-type') || '';
  const payload = contentType.includes('application/json') ? await response.json() : await response.text();

  if (!response.ok) {
    const message = typeof payload === 'string' ? payload : payload.message || 'Request failed';
    throw new Error(message);
  }

  return payload;
}

function formatCurrency(value) {
  if (value == null) return 'N/A';
  return new Intl.NumberFormat('en-IN', { maximumFractionDigits: 0 }).format(value);
}

function renderJobs(jobs) {
  if (!jobs.length) {
    jobsList.innerHTML = '<p>No jobs available yet.</p>';
    return;
  }

  jobsList.innerHTML = jobs.map((job) => `
    <div class="job-card ${job.id === selectedJobId ? 'active' : ''}" data-id="${job.id}">
      <h3>${job.title}</h3>
      <div class="meta">${job.company} · ${job.location}</div>
      <div class="meta">${job.employmentType}</div>
      <div class="salary">₹${formatCurrency(job.salaryMin)} - ₹${formatCurrency(job.salaryMax)}</div>
    </div>
  `).join('');

  jobsList.querySelectorAll('.job-card').forEach((card) => {
    card.addEventListener('click', async () => {
      selectedJobId = Number(card.dataset.id);
      await loadJobDetail(selectedJobId);
      renderJobs(jobs);
    });
  });
}

async function loadJobDetail(jobId) {
  const job = await fetchJson(`/api/jobs/${jobId}`);
  const applications = await fetchJson(`/api/jobs/${jobId}/applications`);

  jobDetail.classList.remove('empty');
  jobDetail.innerHTML = `
    <div>
      <h3>${job.title}</h3>
      <p><strong>Company:</strong> ${job.company}</p>
      <p><strong>Location:</strong> ${job.location}</p>
      <p><strong>Type:</strong> ${job.employmentType}</p>
      <p><strong>Salary:</strong> ₹${formatCurrency(job.salaryMin)} - ₹${formatCurrency(job.salaryMax)}</p>
      <p><strong>Description:</strong> ${job.description}</p>
    </div>
    <ul class="application-list">
      ${applications.length ? applications.map(app => `
        <li class="application-item">
          <div>
            <strong>${app.applicantName}</strong><br>
            ${app.applicantEmail}<br>
            ${app.phone || 'No phone provided'}
          </div>
          <button type="button" class="delete-app-btn" data-app-id="${app.id}">Delete</button>
        </li>
      `).join('') : '<li class="application-item">No applications yet for this role.</li>'}
    </ul>
  `;

  jobDetail.querySelectorAll('.delete-app-btn').forEach((button) => {
    button.addEventListener('click', async (event) => {
      const applicationId = event.currentTarget.dataset.appId;
      try {
        await fetchJson(`/api/applications/${applicationId}`, { method: 'DELETE' });
        if (selectedJobId) {
          await loadJobDetail(selectedJobId);
        }
      } catch (error) {
        alert(error.message);
      }
    });
  });

  applicationForm.classList.remove('hidden');
  applicationForm.dataset.jobId = String(jobId);
}

async function loadJobs() {
  const jobs = await fetchJson('/api/jobs');
  renderJobs(jobs);

  if (selectedJobId) {
    const selected = jobs.find((job) => job.id === selectedJobId);
    if (selected) {
      await loadJobDetail(selectedJobId);
      return;
    }
  }

  if (jobs.length) {
    selectedJobId = jobs[0].id;
    await loadJobDetail(selectedJobId);
  }
}

jobForm.addEventListener('submit', async (event) => {
  event.preventDefault();

  const formData = new FormData(jobForm);
  const payload = Object.fromEntries(formData.entries());
  payload.salaryMin = Number(payload.salaryMin);
  payload.salaryMax = Number(payload.salaryMax);

  try {
    await fetchJson('/api/jobs', {
      method: 'POST',
      body: JSON.stringify(payload),
    });
    jobForm.reset();
    await loadJobs();
  } catch (error) {
    alert(error.message);
  }
});

applicationForm.addEventListener('submit', async (event) => {
  event.preventDefault();
  const jobId = applicationForm.dataset.jobId;
  if (!jobId) {
    alert('Please select a job first.');
    return;
  }

  const formData = new FormData(applicationForm);
  const payload = Object.fromEntries(formData.entries());

  try {
    await fetchJson(`/api/jobs/${jobId}/applications`, {
      method: 'POST',
      body: JSON.stringify(payload),
    });
    applicationForm.reset();
    await loadJobs();
  } catch (error) {
    alert(error.message);
  }
});

reloadBtn.addEventListener('click', loadJobs);

loadJobs().catch((error) => {
  alert(error.message);
});
