/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { InternalConnectorRequestUpdateComponent } from 'app/entities/internal-connector-request/internal-connector-request-update.component';
import { InternalConnectorRequestService } from 'app/entities/internal-connector-request/internal-connector-request.service';
import { InternalConnectorRequest } from 'app/shared/model/internal-connector-request.model';

describe('Component Tests', () => {
  describe('InternalConnectorRequest Management Update Component', () => {
    let comp: InternalConnectorRequestUpdateComponent;
    let fixture: ComponentFixture<InternalConnectorRequestUpdateComponent>;
    let service: InternalConnectorRequestService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [InternalConnectorRequestUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(InternalConnectorRequestUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InternalConnectorRequestUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InternalConnectorRequestService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new InternalConnectorRequest(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new InternalConnectorRequest();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
