# CC-003 – Universal Card UX Blueprint

## Status

**Status:** Draft (v1.0)

**Version:** 1.0

**Owner:** Command Center Architecture

**Depends on:**
- CC-001 – Domain Model
- CC-002 – Universal Card Specification

---

# Purpose

This document defines how users interact with the Universal Card.

Where CC-001 defines the domain and CC-002 defines the Card itself, this document specifies the user experience of creating, reading, editing and progressing Cards throughout their lifecycle.

The objective is to make interacting with a Card feel as natural as writing in a paper notebook while preserving the power of structured digital information.

---

# 1. UX Philosophy

The Universal Card should never feel like a form.

It should feel like writing, thinking and working.

The user captures thoughts first.

Command Center organizes afterwards.

The interface should minimize decisions during capture and maximize clarity during execution.

---

# 2. Design Principles

Every interaction should follow these principles.

- Writing before organizing.
- Calm before information density.
- Progressive disclosure.
- S Pen first.
- AI assists, never interrupts.
- One Card, one experience.

---

# 3. Closed Card

The default state of every Card is collapsed.

Its purpose is rapid recognition.

The user should immediately understand:

- what the Card is;
- what currently needs attention;
- whether action is required.

A Closed Card should contain only essential information.

Visible elements:

- Title
- Last meaningful note (maximum two lines)
- Current Work Phase
- Planning indicator (if applicable)
- Priority indicator (subtle)
- Primary action

No additional metadata should appear by default.

---

# 4. Open Card

Opening a Card reveals the complete working environment.

The transition should feel like opening a notebook rather than navigating to another screen.

The Open Card contains:

## Primary content

- Title
- Main handwritten note
- Additional notes

---

## Operational information

- Work Phase
- Planning
- Priority

---

## Linked information

- Projects
- Contacts
- Calendar items
- Related Cards

---

## Resources

- Attachments
- Images
- Documents

---

## History

Activity timeline.

Visible only when requested.

---

# 5. Editing

Editing begins immediately.

The first writable area receives focus automatically.

Handwriting remains the preferred input method.

Keyboard input is available but secondary.

Editing should never require navigating through multiple dialogs.

---

# 6. Work Phase Transitions

Changing Work Phase should feel like progressing work, not moving files.

Examples:

Action Required

↓

Waiting for Response

↓

Action Required

↓

Completed

↓

Archived

The Card itself never changes identity.

Only its Work Phase changes.

---

# 7. User Actions

Primary actions should always remain visible.

Examples include:

- Save
- Complete
- Waiting for Response
- Delegate
- Reopen
- Archive

Secondary actions should be grouped to reduce visual noise.

---

# 8. Information Hierarchy

Information should appear in this order.

1. Content
2. Work
3. Planning
4. Relationships
5. Metadata
6. History

Content always has priority.

Metadata should never dominate the screen.

---

# 9. AI Interaction

AI remains in the background.

Examples:

The user writes.

↓

Card saved.

↓

AI analyzes.

↓

Suggestions become available.

The user is never interrupted while writing.

---

# 10. Screen Views

Views do not own Cards.

Views are filtered representations.

Examples:

- Today
- Focus
- My Tasks
- Waiting for Response
- Delegated
- Ideas
- Archive

Opening a Card always opens the same Card regardless of the originating view.

---

# 11. Visual Behavior

The interface should communicate calm.

Animations should be subtle.

Transitions should reinforce continuity.

The user should always feel they are working with the same Card.

---

# 12. Future Evolution

Future capabilities should integrate without changing the fundamental interaction model.

Examples include:

- AI summaries
- Voice notes
- OCR
- Collaboration
- Version history
- Rich attachments

These capabilities extend the experience but should never interrupt the primary writing workflow.

---

# Design Principles

The Universal Card experience follows these principles.

- Writing before organizing.
- Calm before complexity.
- Progressive disclosure.
- One Card.
- One workflow.
- One source of truth.
- AI supports.
- S Pen first.
- Every interaction should feel intentional.
- The user should never wonder where information belongs before capturing it.

---

# Non-goals

This document does not define:

- Compose implementation
- Component styling
- Persistence
- Synchronization
- AI implementation