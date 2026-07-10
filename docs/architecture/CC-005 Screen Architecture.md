# CC-005 – Screen Architecture

## Status

**Status:** Draft (v1.0)

**Version:** 1.0

**Last Updated:** 2026-07-10

**Owner:** Command Center Architecture

**Depends on:**
- CC-001 – Domain Model
- CC-002 – Universal Card Specification
- CC-003 – Universal Card UX Blueprint
- CC-004 – Thinking Flow Blueprint

---

# Purpose

This document defines the overall screen architecture of Command Center.

Rather than defining user interface components, it describes the responsibility of every screen, how screens relate to each other, and how they support the Thinking Flow defined in CC-004.

Screens never own data.

Screens are views onto the Universal Card model.

---

# 1. Architectural Principles

The screen architecture follows these principles.

- Every screen has one clear purpose.
- Screens never duplicate information.
- A Card remains the same regardless of which screen opened it.
- Navigation follows the user's workflow.
- Screens present filtered views of the domain model.
- Writing is always faster than organizing.
- Every screen should reduce cognitive load.

---

# 2. Home

## Purpose

Provide the daily starting point for the user.

The Home screen answers one question:

> "What deserves my attention right now?"

Typical content may include:

- Today's most important Cards
- Quick Capture
- Focus items
- Waiting items requiring follow-up
- Recent activity

---

# 3. Today

## Purpose

Show everything requiring attention today.

Selection is based on:

- Planning
- Work Phase
- Priority

This screen supports execution rather than planning.

---

# 4. Focus

## Purpose

Present the user's highest-value work.

Focus should contain only a limited number of Cards.

Cards appear here because they require deliberate attention rather than urgency alone.

---

# 5. My Tasks

## Purpose

Show all Cards where the user is currently responsible for the next action.

Typical Work Phases:

- Action Required
- In Progress

---

# 6. Waiting

## Purpose

Show Cards where another person or external process is expected to act.

Examples:

- Waiting for Response
- Waiting for Approval
- Waiting for Delivery

The purpose of this screen is follow-up rather than execution.

---

# 7. Delegated

## Purpose

Show Cards that have been delegated but remain the user's responsibility to monitor.

Delegated work should remain visible until completed.

---

# 8. Ideas

## Purpose

Provide a safe place for unfinished thinking.

Ideas require no immediate action.

Cards may later evolve into:

- Projects
- Tasks
- Meetings
- Notes

---

# 9. Projects

## Purpose

Provide an overview of Cards grouped by shared outcomes.

Projects do not replace Cards.

Projects organize Cards.

---

# 10. Archive

## Purpose

Provide long-term access to completed work.

Archive supports:

- search
- historical reference
- traceability

Cards remain fully accessible.

---

# 11. Search

## Purpose

Provide universal access to every Card.

Search should operate across:

- Titles
- Handwritten notes
- OCR
- AI summaries
- Attachments
- Linked entities

Search is independent of screen location.

---

# 12. Quick Capture

## Purpose

Capture thoughts immediately.

Requirements:

- opens instantly
- S Pen ready
- minimal interaction
- no mandatory metadata

The goal is preserving thinking.

---

# 13. Card Editor

## Purpose

Provide the complete operational workspace for a Card.

The Card Editor should support:

- writing
- reading
- planning
- linking
- reviewing
- completing

Every Card opens in the same editor regardless of originating screen.

---

# 14. Navigation

Navigation follows work rather than application structure.

Typical flow:

Home

↓

Capture

↓

Card

↓

Work Phase changes

↓

Relevant screen updates automatically

Users should never manually move Cards between screens.

---

# 15. Screen Relationships

Screens are projections of the Universal Card model.

A Card may appear in multiple screens during its lifetime.

Changing Work Phase changes visibility.

Changing visibility never changes Card identity.

---

# 16. Design Principles

The screen architecture follows these principles.

- One Card.
- Many views.
- No duplicated information.
- Navigation follows thinking.
- Every screen has one responsibility.
- Writing before organizing.
- Views are generated from the domain model.
- AI enhances existing screens rather than creating separate AI screens.

---

# Future Evolution

Future screens should extend this architecture rather than introducing parallel workflows.

Potential future screens include:

- Calendar
- Knowledge
- Dashboard widgets
- Collaboration
- Analytics

Every future screen should operate on the Universal Card model while preserving the Thinking Flow established in previous architecture documents.

---

# Non-goals

This document does not define:

- visual design
- typography
- colors
- Compose implementation
- navigation framework
- animations