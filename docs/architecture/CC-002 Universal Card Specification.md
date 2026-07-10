# CC-002 – Universal Card Specification

## Status

**Status:** Approved (v1.0)

**Version:** 1.0

**Last Updated:** 2026-07-10

**Owner:** Command Center Architecture

**Depends on:** CC-001 – Domain Model

---

# Purpose

This document defines the Universal Card model used throughout Command Center.

While **CC-001** defines the domain, this document specifies the operational object that represents work throughout its lifecycle.

A Card is not a task, note, project or reminder.

A Card is the operational representation of work that originates from a Gedachte and evolves while preserving its relationship with the original source.

The Universal Card provides a single, extensible model capable of representing every type of work without introducing duplicate domain models.

---

# 1. Design Goals

The Universal Card exists to:

- represent work consistently throughout the system;
- eliminate duplicate Task, Note and Project models;
- provide one operational model for every workflow;
- preserve traceability back to the originating Gedachte;
- support future AI-assisted organization;
- remain extensible without changing the core architecture.

---

# 2. Relationship with Gedachte

Every Card originates from, or is linked to, one or more Gedachten.

The relationship between Gedachte and Card follows these principles:

- the Gedachte is always the immutable source of truth;
- a Card never replaces the original Gedachte;
- Cards represent operational work;
- transformations create structure without modifying the source;
- multiple Cards may originate from a single Gedachte when work branches into multiple independent streams.

---

# 3. Universal Card Concept

A Card is the universal operational object within Command Center.

Every functional module operates on Cards.

Separate models for Tasks, Notes, Projects or Waiting items are intentionally avoided.

Instead, Cards combine:

- shared core properties;
- independent domain dimensions;
- linked domain entities;
- optional extensions.

The behavior of a Card is determined by its properties rather than by its object type.

Examples of representations using the same Card model include:

- Task
- Project
- Meeting
- Contact
- Note
- Idea
- Waiting
- Reference
- Archive item

One Card.

Many representations.

---

# 4. Domain Dimensions

Every Card consists of several independent dimensions.

These dimensions must never be coupled.

---

## Origin

Where did this Card come from?

Examples:

- Gedachte
- Manual
- AI-assisted
- Import

Origin never changes.

---

## Context

What is this Card about?

Examples:

- Project
- Person
- Client
- Subject
- Meeting
- Idea

Context is relatively stable.

---

## Work Phase

What is currently happening?

Examples:

- Action Required
- Waiting for Response
- Delegated
- Scheduled
- In Progress
- Completed
- Archived

Work Phase changes continuously during the lifetime of a Card.

---

## Priority

Relative importance.

Examples:

- High
- Normal
- Low

Priority is independent from Context and Work Phase.

---

## Planning

When should attention be given?

Examples:

- Today
- This Week
- Specific Date
- Someday

Planning changes independently from all other dimensions.

---

# 5. Core Properties

Every Card should include at least the following properties.

## Identity

- Unique ID
- Title

---

## Content

- Description
- Handwritten content
- Source Gedachte reference(s)

---

## Operational Properties

- Work Phase
- Priority
- Planning

---

## Metadata

- Created date
- Modified date
- Tags

---

## Relationships

- Linked Cards
- Linked Projects
- Linked Calendar Items
- Linked Contacts

---

## Resources

- Attachments
- Images
- Documents
- Media

Implementation guideline:

Optional functionality should be implemented using composition or extension objects rather than creating specialized Card subclasses.

---

# 6. Card Lifecycle

A Gedachte is static.

A Card is dynamic.

The lifecycle of a Card represents operational progress.

Example:

Gedachte

↓

Processing

↓

Card

↓

Action Required

↓

Waiting for Response

↓

Action Required

↓

Delegated

↓

Waiting for Response

↓

Completed

↓

Archived

Important characteristics:

- a Card may transition between Work Phases multiple times;
- Work Phase does not change Card identity;
- the originating Gedachte remains unchanged;
- lifecycle represents work progression rather than processing state.

---

# 7. Views

Screens within Command Center are projections of the Universal Card model.

Examples:

- Today
- Focus
- My Tasks
- Waiting for Response
- Delegated
- Ideas
- Archive

Views are generated from Card properties.

Views are **not** separate domain models.

A Card may appear in different views throughout its lifetime without changing its identity.

---

# 8. Extensibility

Future functionality extends the Universal Card through composition.

Examples include:

- AI summaries
- OCR
- Voice notes
- Documents
- Images
- Checklists
- Location
- Reminders
- Version history
- Collaboration

The Universal Card contract should remain stable.

Extensions should never replace the core Card model.

---

# 9. User Interaction Principles

The Universal Card should support calm, low-friction interaction.

Primary principles:

- S Pen is the primary input method.
- Capturing is always faster than organizing.
- Reading should require minimal cognitive effort.
- Editing should feel natural.
- Metadata never dominates content.
- Progressive disclosure prevents information overload.

The user should never be required to determine where a Card belongs before capturing a Gedachte.

Organization follows capture.

---

# 10. AI Integration Principles

AI operates on top of the Universal Card.

AI may:

- interpret;
- summarize;
- classify;
- suggest relationships;
- propose planning;
- recommend Context;
- recommend Priority.

AI may never:

- overwrite the originating Gedachte;
- replace user intent;
- remove source traceability.

The user always remains in control.

---

# 11. Design Principles

The Universal Card follows these architectural principles:

- One universal Card model.
- One operational object.
- One source of truth.
- Composition over duplication.
- Capture before organization.
- Work evolves; identity does not.
- Context and Work Phase are independent.
- Views are projections of the domain model.
- AI enriches the Card without replacing it.
- Every future feature integrates into the Universal Card.

---

# 12. Future Implementation Guidance

Every future module should integrate with the Universal Card.

Do not introduce independent Task, Note or Project models.

New capabilities should extend the Card through composition.

Every future workflow should operate on the Universal Card while preserving traceability to the originating Gedachte.

This guarantees long-term consistency, scalability and maintainability across the entire Command Center ecosystem.

---

# Non-goals

This document does not define:

- screen layouts;
- visual design;
- Compose implementation;
- persistence technology;
- synchronization strategy;
- AI provider implementation.