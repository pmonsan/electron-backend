/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { UserConnectionUpdateComponent } from 'app/entities/user-connection/user-connection-update.component';
import { UserConnectionService } from 'app/entities/user-connection/user-connection.service';
import { UserConnection } from 'app/shared/model/user-connection.model';

describe('Component Tests', () => {
  describe('UserConnection Management Update Component', () => {
    let comp: UserConnectionUpdateComponent;
    let fixture: ComponentFixture<UserConnectionUpdateComponent>;
    let service: UserConnectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [UserConnectionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserConnectionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserConnectionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserConnectionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserConnection(123);
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
        const entity = new UserConnection();
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
