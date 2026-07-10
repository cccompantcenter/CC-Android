# Alpha 1.1 – Universal Card Experience

## Goal

Transform the Universal Card from a form into a calm thinking workspace.

This sprint focuses only on layout, information hierarchy and user experience.

No new functionality may be introduced.

---

## Scope

Refactor the existing Universal Card Editor.

Maintain all existing functionality.

Only improve:

- visual hierarchy
- layout
- spacing
- grouping
- interaction flow

---

## Requirements

### Header

- Cleaner header
- No instructional text
- Card title is dominant
- Context shown as subtle subtitle

### Writing Area

- Writing becomes the primary focus
- Large uninterrupted writing area
- No form feeling

### Metadata

Move metadata into a collapsible Details section.

Includes:

- Work Phase
- Priority
- Planning
- Tags
- Favorite

Collapsed by default.

### Action Bar

Create one fixed action bar.

Contains:

- Back
- Save
- Complete
- More

Use the same component everywhere.

### Constraints

Do not change:

- repository structure
- data model
- navigation
- S Pen component
- save logic

---

## Acceptance Criteria

The editor should feel like writing in a notebook rather than completing a form.

No existing functionality may regress.

All existing tests should continue to pass.